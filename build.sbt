ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "ASKIT",
    libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.4.0",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.6",
    libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.2.8",
    libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.20",
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.20",
    libraryDependencies += "ch.megard" %% "akka-http-cors" % "1.2.0"

  )
