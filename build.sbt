name := "WeatherAPITesting"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "io.cucumber" % "cucumber-core" % "2.0.1" % "test",
  "io.cucumber" %% "cucumber-scala" % "2.0.1" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "io.rest-assured" % "scala-support" % "3.3.0",
  "com.waioeka.sbt" %% "cucumber-runner" % "0.1.5",
  "com.typesafe" % "config" % "1.3.3")


enablePlugins(CucumberPlugin)

CucumberPlugin.glue := "com/waioeka/sbt/"

val framework = new TestFramework("com.waioeka.sbt.runner.CucumberFramework")
testFrameworks += framework

// Configure the arguments.
testOptions in Test += Tests.Argument(framework, "--glue", "")
testOptions in Test += Tests.Argument(framework, "--plugin", "pretty")
unmanagedClasspath in Test += baseDirectory.value / "src/test/scala/features"

// Any environment properties you want to override/set.
CucumberPlugin.envProperties := Map("K"->"2049")
