ThisBuild / scalaVersion := "3.3.3"

lazy val `flexmark-util-misc` = project.in(file("flexmark-util-misc"))

lazy val root = project.in(file("."))
  .aggregate(`flexmark-util-misc`)
