import Dependencies.{ CompilerPlugin, Libraries }
import io.github.davidgregory084.TpolecatPlugin.autoImport.{ tpolecatExcludeOptions, tpolecatScalacOptions }
import sbt.{ AutoPlugin, Test, TestFramework }
import sbt.Keys.{ libraryDependencies, testFrameworks }
import wartremover.WartRemover.autoImport.wartremoverErrors

object DefaultProjectSettings extends AutoPlugin {

  override def projectSettings = Seq(
    tpolecatExcludeOptions ++= CompilerOptions.excluded,
    libraryDependencies ++= Seq(
      CompilerPlugin.kindProjector,
      CompilerPlugin.betterMonadicFor,
      CompilerPlugin.semanticDB,
      Libraries.zioCore,
      Libraries.zioTest         % Test,
      Libraries.zioTestSbt      % Test,
      Libraries.zioTestMagnolia % Test,
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    wartremoverErrors ++= WartOptions.default,
  )

}
