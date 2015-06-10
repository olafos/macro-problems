import sbt.{TestFrameworks, Tests}

name := "macro-problems"

organization := "pl.codeplay"

version := "0.0.0"

scalaVersion := "2.11.6"

scalacOptions ++= Seq("-feature", "-deprecation")

testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-q", "-v", "-s", "-a")

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oDS")

parallelExecution in Test := false

logBuffered := false

net.virtualvoid.sbt.graph.Plugin.graphSettings

org.scalastyle.sbt.ScalastylePlugin.Settings

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "2.2.5" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test",
  "junit" % "junit" % "4.12" % "test"
)
