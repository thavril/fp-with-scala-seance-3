val scala3Version = "3.3.7"

lazy val root = project
  .in(file("."))
  .settings(
    name := "fp-scala-seance-3",
    version := "2.0.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test
    )
  )
