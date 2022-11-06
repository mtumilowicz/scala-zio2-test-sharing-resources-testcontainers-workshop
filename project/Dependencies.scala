import sbt._

object Dependencies {

  object V {
    val cats    = "2.8.0"
    val circe   = "0.14.3"
    val refined = "0.10.1"
    val logback = "1.4.4"

    val betterMonadicFor = "0.3.1"
    val kindProjector    = "0.13.2"
    val organizeImports  = "0.6.0"
    val semanticDB       = "4.6.0"

    val httpClient = "3.8.2"
    val zio        = "2.0.2"
    val zioConfig  = "3.0.2"
    val zioHttp    = "2.0.0-RC11"
    val zioJson    = "0.3.0"

    val testContainers = "0.40.11"
  }

  object Libraries {

    private def circe(artifact: String): ModuleID = "io.circe" %% s"circe-$artifact" % V.circe

    private def zio(artifact: String): ModuleID = "dev.zio" %% artifact % V.zio

    private def zioConfig(artifact: String): ModuleID = "dev.zio" %% artifact % V.zioConfig

    private def cats(artifact: String): ModuleID = "org.typelevel" %% artifact % V.cats

    private def httpClient(artifact: String): ModuleID = "com.softwaremill.sttp.client3" %% artifact % V.httpClient

    private def refined(artifact: String): ModuleID = "eu.timepit" %% artifact % V.refined

    val catsCore   = cats("cats-core")
    val catsKernel = cats("cats-kernel")

    val circeCore    = circe("core")
    val circeLiteral = circe("literal")
    val circeParser  = circe("parser")

    val zioCore           = zio("zio")
    val zioConfigCore     = zioConfig("zio-config")
    val zioConfigMagnolia = zioConfig("zio-config-magnolia")
    val zioConfigRefined  = zioConfig("zio-config-refined")
    val zioConfigTypesafe = zioConfig("zio-config-typesafe")
    val zioHttp           = "io.d11" %% "zhttp" % V.zioHttp
    val zioJson           = "dev.zio" %% "zio-json" % V.zioJson
    val zioTest           = zio("zio-test")
    val zioTestSbt        = zio("zio-test-sbt")
    val zioTestMagnolia   = zio("zio-test-magnolia")

    val httpClientCore = httpClient("async-http-client-backend-zio")
    val httpClientJson = httpClient("zio-json")

    val refinedCore = refined("refined")
    val refinedCats = refined("refined-cats")

    // Runtime
    val logback = "ch.qos.logback" % "logback-classic" % V.logback

    // Test
    val testContainers = "com.dimafeng" %% "testcontainers-scala-scalatest" % V.testContainers

    // Scalafix rules
    val organizeImports = "com.github.liancheng" %% "organize-imports" % V.organizeImports
  }

  object CompilerPlugin {
    val betterMonadicFor = compilerPlugin(
      "com.olegpy" %% "better-monadic-for" % V.betterMonadicFor
    )
    val kindProjector = compilerPlugin(
      "org.typelevel" % "kind-projector" % V.kindProjector cross CrossVersion.full
    )
    val semanticDB = compilerPlugin(
      "org.scalameta" % "semanticdb-scalac" % V.semanticDB cross CrossVersion.full
    )
  }

  val order = Seq(
    Libraries.logback % Runtime,
    Libraries.catsCore,
    Libraries.refinedCore,
    Libraries.refinedCats,
    Libraries.zioConfigCore,
    Libraries.zioConfigMagnolia,
    Libraries.zioConfigRefined,
    Libraries.zioConfigTypesafe,
    Libraries.zioHttp,
  )

  val `order-http-api` = Seq(
    Libraries.zioJson,
  )

  val customer = Seq(
    Libraries.logback % Runtime,
    Libraries.catsCore,
    Libraries.zioConfigCore,
    Libraries.zioConfigMagnolia,
    Libraries.zioConfigRefined,
    Libraries.zioConfigTypesafe,
    Libraries.refinedCore,
    Libraries.refinedCats,
    Libraries.httpClientCore,
    Libraries.httpClientJson,
    Libraries.testContainers,
    Libraries.zioHttp,
  )

  val `integration-test` = Seq(
    Libraries.circeParser,
    Libraries.httpClientCore,
    Libraries.refinedCore,
    Libraries.zioConfigCore,
    Libraries.zioConfigMagnolia,
    Libraries.zioConfigRefined,
    Libraries.zioConfigTypesafe,
    Libraries.circeLiteral    % IntegrationTest,
    Libraries.zioTest         % IntegrationTest,
    Libraries.zioTestMagnolia % IntegrationTest,
    Libraries.zioTestSbt      % IntegrationTest,
  )

  val `test-containers` = Seq(
    Libraries.testContainers,
  )

}
