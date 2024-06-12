ThisBuild / scalaVersion := "3.3.3"

lazy val `scalamd-core` = project.in(file("scalamd-core"))

lazy val `flexmark-util-collection` = project
  .in(file("flexmark-util-collection"))
  .dependsOn(`scalamd-core`)

lazy val root = project
  .in(file("."))
  .aggregate(`scalamd-core`, `flexmark-util-collection`)
