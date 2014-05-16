import play.Project._

name := "ratings"

version := "1.0"

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.2"
)

playScalaSettings