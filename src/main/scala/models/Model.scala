package models

import org.mongodb.scala.result.InsertOneResult
import org.mongodb.scala._
import org.mongodb.scala.bson.collection.immutable.Document
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Model {


  // Define MongoDB connection string
  val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
  // Get a handle to the database
  val database: MongoDatabase = mongoClient.getDatabase("Testing")
  // Get a handle to the collection
  val collection: MongoCollection[Document] = database.getCollection("content")

  // Retrieve all documents from MongoDB
  def getAllDocuments(): Seq[Document] = {
    val result: Future[Seq[Document]] = collection.find().toFuture()
    Await.result(result, Duration.Inf)
  }

  // Insert a document into MongoDB
  def insertDocument(document: Document): InsertOneResult = {
    val result: Future[InsertOneResult] = collection.insertOne(document).toFuture()
    Await.result(result, Duration.Inf)
  }



}
