package models

import org.mongodb.scala.result.InsertOneResult
import org.mongodb.scala._
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.result.UpdateResult
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.model.Updates
import scala.concurrent.Future
import scala.concurrent.Await
import org.mongodb.scala.model.Filters._


object Model {

  // Define MongoDB connection string
  val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
  // Get a handle to the database
  val database: MongoDatabase = mongoClient.getDatabase("Testing")
  // Get a handle to the collection
  val collection: MongoCollection[Document] = database.getCollection("posts")
  //get all tags
  val tags: MongoCollection[Document] = database.getCollection("tags")

  // Retrieve all documents from MongoDB
  def getAllDocuments(): Seq[Document] = {
    val result: Future[Seq[Document]] = collection.find().toFuture()
    Await.result(result, Duration.Inf)
  }

  // get all tags
  def getAllTags(): Seq[Document] = {
    val result: Future[Seq[Document]] = tags.find().toFuture()
    Await.result(result, Duration.Inf)
  }

  // Insert a new document into the content collection
  def insertNewQst(data: Document): InsertOneResult = {
    val result: Future[InsertOneResult] = collection.insertOne(data).toFuture()
    Await.result(result, Duration.Inf)
  }

  // Retrieve a specific document from MongoDB based on its unique identifier
  def getDocumentById(id: String): Option[Document] = {
    val result: Future[Option[Document]] = collection.find(Document("post_id" -> id)).headOption()
    Await.result(result, Duration.Inf)
  }

  // Retrieve a specific user document from MongoDB based on its unique identifier
  def getDocumentByUser(user: String): Option[Document] = {
    val result: Future[Option[Document]] = collection.find(Document("user" -> user)).headOption()
    Await.result(result, Duration.Inf)
  }

  // Insert a new answer into the answers array of a specific document
  def insertAnswer(postId: String, answer: Document): UpdateResult = {
    val filter = equal("post_id", postId)
    val update = Updates.push("answers", answer)
    val result: Future[UpdateResult] = collection.updateOne(filter, update).toFuture()
    Await.result(result, Duration.Inf)
  }

  // Update the upvotes of a specific answer in the answers array of a document
  def updateAnswerUpvotes(postId: String, answerId: String, newUpvotes: Int): UpdateResult = {
    val filter = and(equal("post_id", postId), equal("answers.answer_id", answerId))
    val update = Updates.set("answers.$.upvotes", newUpvotes.toString)
    val result: Future[UpdateResult] = collection.updateOne(filter, update).toFuture()
    Await.result(result, Duration.Inf)
  }





}
