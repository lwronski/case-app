import sbt._
import sbt.Keys._

object Settings {

  private def scala212 = "2.12.16"
  private def scala213 = "2.13.8"

  private lazy val isAtLeastScala213 = Def.setting {
    import Ordering.Implicits._
    CrossVersion.partialVersion(scalaVersion.value).exists(_ >= (2, 13))
  }

  lazy val shared = Seq(
    scalaVersion       := scala213,
    crossScalaVersions := Seq(scala212, scala213),
    scalacOptions ++= Seq(
      "-target:jvm-1.8",
      "-feature",
      "-deprecation"
    ),
    libraryDependencies ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, v)) if v >= 13 =>
          // if scala 2.13.0-M4 or later, macro annotations merged into scala-reflect
          // https://github.com/scala/scala/pull/6606
          Nil
        case _ =>
          compilerPlugin(Deps.macroParadise) :: Nil
      }
    },
    scalacOptions ++= {
      if (isAtLeastScala213.value) Seq("-Ymacro-annotations")
      else Nil
    },
    autoAPIMappings := true
  )

  lazy val caseAppPrefix =
    name := {
      val shortenedName = name.value
        .stripSuffix("JVM")
        .stripSuffix("JS")
        .stripSuffix("Native")
      "case-app-" + shortenedName
    }

}
