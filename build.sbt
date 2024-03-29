// import com.github.play2war.plugin._

name := """ie-validation"""

version := "1.0-SNAPSHOT"

// Play2WarPlugin.play2WarSettings

// Play2WarKeys.servletVersion := "3.1"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

libraryDependencies ++= Seq(
  javaJdbc,
//  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "8.0.16",
  "com.google.code.gson" % "gson" % "2.3.1"
)

scalaVersion := "2.12.18"

routesGenerator := InjectedRoutesGenerator