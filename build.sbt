sbtPlugin := true

name := """flyway-sqlfile-extension"""

organization := "com.github.hidai620"

version := "0.1.0"

//scalaVersion := "2.11.7"
scalaVersion := "2.10.6"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.flywaydb" % "flyway-sbt" % "3.2.1"
)
  

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

