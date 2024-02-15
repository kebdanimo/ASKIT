package models

import org.mongodb.scala.result.InsertOneResult
import org.mongodb.scala._
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.result.UpdateResult
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import org.mongodb.scala.bson.ObjectId

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


}
