package controllers

import models.Model
import org.mongodb.scala.bson.collection.immutable.Document

object Controller {

  // Get all documents from the model (MongoDB)
  def getAllData(): Seq[Document] = Model.getAllDocuments()

  // Insert a new document into MongoDB
  def insertData(document: Document): Unit = Model.insertDocument(document)

}
