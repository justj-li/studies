name := "Simple App"

version := "0.1"

/*
 * From [ https://hortonworks.com/tutorial/setting-up-a-spark-development-environment-with-scala/ ]
 * It is important to remember that each version of Spark is designed to be
 * compatible with a specific version of Scala, so Spark might not compile
 * or run correctly if you use the wrong version of Scala.
 * For Spark 2.3.x you need to use Scala 2.11.12
 */
scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.1"