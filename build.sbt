import Dependencies._

name := "scala-zio2-test-sharing-resources-testcontainers-workshop"

ThisBuild / scalafixDependencies += Libraries.organizeImports

ThisBuild / scalaVersion := "2.13.10"

ThisBuild / Test / fork := true

SbtCommandAliases.fix
SbtCommandAliases.fmtAll
SbtCommandAliases.importFix
SbtCommandAliases.unusedDeps
SbtCommandAliases.transitiveDeps
SbtCommandAliases.publishLocalAll

lazy val order = (project in file("order"))
  .enablePlugins(DefaultProjectSettings)
  .enablePlugins(DockerPlugin)
  .enablePlugins(AshScriptPlugin)
  .settings(
    name := "order",
    libraryDependencies ++= Dependencies.order,
    Docker / packageName := "order",
    dockerBaseImage := "openjdk:11-jre-slim-buster",
    dockerExposedPorts ++= Seq(8080),
    dockerUpdateLatest := true,
    dockerCommands ++= DockerCommands.installCurl,
  )
  .dependsOn(`order-http-api` % "compile->compile;test->test")

lazy val `order-http-api` = (project in file("order/http-api"))
  .enablePlugins(DefaultProjectSettings)
  .enablePlugins(DockerPlugin)
  .enablePlugins(AshScriptPlugin)
  .settings(
    name := "order-http-api",
    libraryDependencies ++= Dependencies.`order-http-api`,
  )

lazy val customer = (project in file("customer"))
  .enablePlugins(DefaultProjectSettings)
  .enablePlugins(DockerPlugin)
  .enablePlugins(AshScriptPlugin)
  .settings(
    name := "customer",
    libraryDependencies ++= Dependencies.customer,
    Docker / packageName := "customer",
    dockerBaseImage := "openjdk:11-jre-slim-buster",
    dockerExposedPorts ++= Seq(8080),
    dockerUpdateLatest := true,
    dockerCommands ++= DockerCommands.installCurl,
    unusedCompileDependenciesFilter -= moduleFilter("com.softwaremill.sttp.client3", "async-http-client-backend-zio"),
  )
  .dependsOn(
    `order-http-api`  % "compile->compile;test->test",
    `test-containers` % "compile->compile;test->test",
  )

lazy val `integration-test` = (project in file("integration-test"))
  .enablePlugins(DefaultProjectSettings)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings)
  .settings(
    name := "integration-test",
    tpolecatExcludeOptions ++= CompilerOptions.excluded,
    libraryDependencies ++= Dependencies.`integration-test`,
  )

lazy val `test-containers` = (project in file("test-containers"))
  .enablePlugins(DefaultProjectSettings)
  .settings(
    name := "test-containers",
    libraryDependencies ++= Dependencies.`test-containers`,
  )
