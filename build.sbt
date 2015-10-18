name := "clustr"

version := "1.0"

lazy val `clustr` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq( jdbc , anorm , cache , ws, "mysql" % "mysql-connector-java" % "5.1.18")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  