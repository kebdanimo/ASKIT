package routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.StatusCodes
import controllers.Controller
import org.mongodb.scala.bson.collection.immutable.Document
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.Directives._
import models.Model

object Routes {

  // Define CORS settings
  val corsSettings = CorsSettings.defaultSettings.withAllowGenericHttpRequests(true)

  // routes
  val routes: Route =
    path("data") {
      get {
        val documents: Seq[Document] = Controller.getAllData()
        complete(StatusCodes.OK, documents.map(_.toJson()).mkString("[", ",", "]"))
      } ~
        post {
          entity(as[String]) { jsonString =>
            val document = Document(jsonString)
            Controller.insertData(document)
            complete(StatusCodes.Created, "Document inserted successfully")
          }
        }
    }
  // Define your new route
  val newRoute: Route =
    path("new") {
      get {
        // Your GET request handling logic goes here
        val documents: Seq[Document] = Controller.getAllTags()
        complete(StatusCodes.OK, documents.map(_.toJson()).mkString("[", ",", "]"))
      }
    }

  // Define your new route
  val targetRoute: Route =
    path("document" / Segment) { id =>
      get {
        // Retrieve the document by its ID
        val maybeDocument: Option[Document] = Model.getDocumentById(id)
        maybeDocument match {
          case Some(document) => complete(StatusCodes.OK, document.toJson())
          case None => complete(StatusCodes.NotFound, s"Document with ID $id not found")
        }
      }
    }

  // Enable CORS for all routes
  val corsRoutes = cors(corsSettings) {
    routes ~ newRoute ~ targetRoute
  }
  // OPTIONS route for handling preflight requests
  val optionsRoute = options {
    complete(StatusCodes.OK)
  }
  // Combine all routes including OPTIONS route
  val allRoutes = cors(corsSettings) {
    optionsRoute ~ corsRoutes
  }

}
