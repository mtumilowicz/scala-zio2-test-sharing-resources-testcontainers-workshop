[![Build Status](https://app.travis-ci.com/mtumilowicz/scala-zio2-test-sharing-resources-testcontainers-workshop.svg?branch=master)](https://app.travis-ci.com/mtumilowicz/scala-zio2-test-sharing-resources-testcontainers-workshop)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

# scala-zio2-test-sharing-resources-testcontainers-workshop
* references
    * [What's Cooking in ZIO Test by Adam Fraser](https://www.youtube.com/watch?v=JtfdcxgQ71E)
    * https://sttp.softwaremill.com/en/latest/json.html
    * https://zio.dev/guides/tutorials/configurable-zio-application/
    * https://typelevel.org/cats/typeclasses/eq.html
    * https://zio.dev/guides/tutorials/configurable-zio-application/
    * [Zymposium - Sharing Expensive Services Across Specs](https://www.youtube.com/watch?v=gzHStYNa6Og)
    * https://blog.rockthejvm.com/zio-http/
    * https://zio.dev/reference/test/sharing-layers-between-multiple-files
    * https://github.com/gvolpe/pfps-shopping-cart
    * https://github.com/sbt/sbt-native-packager/
    * https://www.scala-sbt.org/sbt-native-packager/
    * https://stackoverflow.com/questions/60502244/can-i-inspect-my-scala-codebase-to-find-all-warnings-of-type-comparing-unrelat
    * https://medium.com/swlh/alpine-slim-stretch-buster-jessie-bullseye-bookworm-what-are-the-differences-in-docker-62171ed4531d
    * https://techoverflow.net/2021/01/13/how-to-use-apt-install-correctly-in-your-dockerfile/
    * https://www.wartremover.org
    * https://github.com/typelevel/scalac-options
    * https://tpolecat.github.io/2017/04/25/scalac-flags.html
    * https://www.scala-sbt.org/0.13.0/docs/Detailed-Topics/Testing#integration-tests
    * https://github.com/cb372/sbt-explicit-dependencies
    * https://www.scala-sbt.org/1.x/docs/Custom-Settings.html
    * https://www.scala-sbt.org/1.x/docs/Testing.html
    * [Getting started with #Scalafix](https://www.youtube.com/watch?v=Xl8oOmFNGgQ)
    * [100 with ZIO Test by Adam Fraser: Scala in the City Conference](https://www.youtube.com/watch?v=qDFfVinjDPQ)
    * https://github.com/adamgfraser/0-to-100-with-zio-test
    * [Easy Config For Your App by Afsal Thaj](https://www.youtube.com/watch?v=4SrSKluyyKo)
    * https://www.testcontainers.org
    * https://github.com/testcontainers/testcontainers-scala
    * https://github.com/scalameta/sbt-scalafmt
    * https://scalameta.org/scalafmt/
    * https://github.com/scalacenter/sbt-scalafix
    * https://scalameta.org/docs/semanticdb/guide.html
    * https://github.com/typelevel/sbt-tpolecat
    * https://www.scala-sbt.org/1.x/docs/Forking.html
    * https://www.scala-sbt.org/1.x/docs/Testing.html
    * https://www.scala-sbt.org/1.x/docs/Plugins.html
    * https://www.scala-sbt.org/1.x/docs/Basic-Def.html
    * https://www.scala-sbt.org/1.x/docs/Task-Graph.html
    * https://www.scala-sbt.org/1.x/docs/Library-Dependencies.html
    * https://github.com/cakesolutions/sbt-cake/blob/master/project/project/CakePlatformDependencies.scala
    * https://www.scala-sbt.org/1.x/docs/Using-Plugins.html
    * https://www.scala-sbt.org/1.x/docs/Cross-Build.html
    * https://medium.com/bigpanda-engineering/cross-compiling-in-scala-bb491682ffcf
    * https://www.scala-sbt.org/1.x/docs/Cross-Build.html
    * https://stackoverflow.com/questions/65836024/scala-binary-vs-scala-full-version-convention
    * https://www.scala-sbt.org/1.x/api/sbt/AutoPlugin.html
    * https://www.scala-sbt.org/1.x/docs/Plugins-Best-Practices.html
    * https://zio.dev/reference/test/sharing-layers-between-multiple-files/
    * https://medium.com/adaltas/spark-streaming-part-3-devops-tools-and-tests-for-spark-applications-f4f20e3f4f85
    * https://www.scala-sbt.org/1.x/docs/Compiler-Plugins.html
    * https://stackoverflow.com/questions/66281287/sbt-plugins-vs-compiler-plugins
    * https://docs.scala-lang.org/overviews/plugins/index.html
    * https://github.com/oleg-py/better-monadic-for
    * https://github.com/typelevel/kind-projector
    * https://docs.travis-ci.com/user/job-lifecycle/#customizing-the-build-phase

## preface
* goals of this workshop
    * showing example of multimodule scala project
    * sbt basics
        * settings
        * dependencies
        * compiler options
        * plugins
    * introduction to plugins
        * sbt-native-packager"
        * sbt-scalafmt"
        * sbt-explicit-dependencies"
        * sbt-scalafix"
        * sbt-wartremover"
        * sbt-tpolecat"
    * introduction to compiler plugins
        * better-monadic-for
        * kind-projector
        * semanticdb-scalac
    * overview of testcontainers
        * how to create them
        * how to use them
    * zio tests
        * sharing layers inside the suite and across suites
* project
    * to run IT tests
        * run docker desktop
        * publishLocal order and customer
            * `publishLocalAll` in sbt shell
        * `docker-compose up -d` in the terminal (root directory)
        * `it:test` in sbt shell
* workshops
    * task1
        1. modify OrderContainer to log a message after it is started
        1. verify that OrderContainer is started once for 2Spec & 3Spec, and is started once again for Spec
    * task2
        1. modify WartOptions.default with some option
        1. violate that option in the code
        1. verify that compilation fails
    * task3
        1. implement DockerSettingsPlugin
            * requires DockerPlugin
            * has to settings
                ```
                object autoImport {
                  val dockerImageName = settingKey[String]("equivalent of Docker / packageName")
                  val dockerExposedPort = settingKey[Int]("equivalent of dockerExposedPorts but with only one port")
                }
                ```
            * sets by default
                ```
                dockerBaseImage := "openjdk:11-jre-slim-buster",
                dockerUpdateLatest := true,
                ```
        1. plug it into order module and verify that it works
    * task4
        1. add sbt command alias for fmt and import fix

## sbt build
* defined in `build.sbt`
    * consists set of subproject
    * sbt is recursive
        * `build.sbt` conceals how sbt really works
            * sbt builds are defined with Scala code and that code, itself, has to be built
        * `project` directory is another build inside your build
            * knows how to build your build
            * projects inside the metabuild can do anything any other project can do
                * you can tweak the build definition of the build definition project, by creating a `project/project/` directory
        * to distinguish the builds we sometimes use the term
            * proper build to refer to your build
            * meta-build to refer to the build in project
* example
    ```
    lazy val root = (project in file(".")) // lazy vals to avoid initialization order problems
      .settings(
        name := "Hello",
        scalaVersion := "2.12.7"
      )
    ```
* settings
    * example
        ```
        name := "Hello"
        ```
    * structure
        * left-hand side: key
            * instance of
                * `SettingKey[T]`
                    * evaluated only once
                        * can’t depend on a task, not re-run
                    * computed when loading the subproject
                * `TaskKey[T]`
                    * evaluated each time it’s referred to
                    * analogy: scala function
                    * potentially: side effects
                * or `InputKey[T]`
                    * key for a task that has command line arguments as input
                * `T` is the expected value type
                    ```
                    name := 42  // will not compile, name is typed to SettingKey[String]
                    ```
                * custom keys may be defined with their respective creation methods
                    * `settingKey`
                    * `taskKey`
                    * and `inputKey`
            * example
                * built-in keys are just fields in an object called `Keys`
                    * build.sbt implicitly has an import `sbt.Keys._`
        * operator
            * `:=` operator on name is also typed specifically to String
                * you can assign a value to a setting and a computation to a task
        * right-hand side: body
    * can be written directly into the build.sbt file
        * instead of putting them inside `.settings(...)`
        * called "bare style"
        * example
            ```
            ThisBuild / version := "1.0"
            ThisBuild / scalaVersion := "2.12.16"
            ```
    * more than key-value pairs
        * directed acyclic graph (DAG) of tasks where the edges denote happens-before
            * use .value method to express the dependency to another task or setting
                * is not a normal Scala method call
                * build.sbt DSL uses a macro to lift these outside of the task body
            * example
                ```
                lazy val root = (project in file("."))
                  .settings(
                    name := "Hello",
                    scalacOptions := {
                      val out = streams.value // happens-before scalacOptions (in particular: before logging)
                      val log = out.log
                      log.info("123")
                      val ur = update.value   // happens-before scalacOptions (in particular: before logging)
                      log.info("456")
                      ur.allConfigurations.take(3)
                    }
                  )
                ```
                * output
                    ```
                    > scalacOptions
                    [info] Updating {file:/xxx/}root...
                    [info] Resolving jline#jline;2.14.1 ...
                    [info] Done updating.
                    [info] 123
                    [info] 456
                    [success] Total time: 0 s, completed Jan 2, 2017 10:38:24 PM
                    ```
                * observation
                    * no guarantee about the ordering of update and clean tasks
                        * might run update then clean, clean then update, or both in parallel
                * digression
                    ```
                    if (false) {
                        val x = clean.value // will run always
                    }
                    ```
* library dependencies
    * example
        ```
        libraryDependencies += groupID % artifactID % revision // default: Compilation
        libraryDependencies += groupID % artifactID % revision % configuration // string or a Configuration value, ex. Test
        libraryDependencies += "org.apache.derby" % "derby" % "10.4.1.3" % Test
        ```
    * declared in Keys
        ```
        libraryDependencies = settingKey[Seq[ModuleID]]("Declares managed dependencies.")
        ```
    * % method
        * create ModuleID objects from strings
    * %% method
        * sbt will add your project’s binary Scala version to the artifact name
        * dependencies are compiled for multiple Scala versions, and you’d like to get the one that
        matches your project to ensure binary compatibility
        * `groupID %% artifactID % revision` is translated into `groupID % artifactID_scalaVersion % revision`
        * example
            ```
            libraryDependencies += "org.scala-stm" %% "scala-stm" % "0.9.1"
            libraryDependencies += "org.scala-stm" % "scala-stm_2.13" % "0.9.1" // equivalent, assuming the scalaVersion for your build is 2.13.8
            ```
* resolvers
    * not all packages live on the same server
        * default: standard Maven2 repository
    * configuration
        ```
        resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
        ```

## integration tests
* the following full build configuration demonstrates integration tests
    ```
    lazy val root = (project in file("."))
      .configs(IntegrationTest) // adds the predefined integration test configuration
      .settings(
        Defaults.itSettings, // adds compilation, packaging, and testing actions and settings in the IntegrationTest configuration
        libraryDependencies += ...
      )
    ```
* to define a dependency only for integration tests, use `%IntegrationTest`
* standard source hierarchy is used
    * sources: `src/it/scala`
    * resources: `src/it/resources`
* standard testing tasks must be prefixed with `IntegrationTest/`
    * `IntegrationTest/test`
    * `it:test`

## testcontainers
* lightweight, throwaway instances of common databases, Selenium web browsers, or anything else that
can run in a Docker container
* use cases
    * data access layer integration tests
        * MySQL, PostgreSQL, etc
    * application integration tests
        * databases, message queues or web servers
    * UI/Acceptance tests
        * web browsers, compatible with Selenium, for conducting automated UI tests
* creating container
    * `ContainerDef` - container definition
        * describes, how to build a container
        * has a `start(`) method
            * returns a started Container
    * `Container` - started container
    * example
    ```
    class NginxContainer(port: Int, underlying: GenericContainer) extends GenericContainer(underlying) {
      def rootUrl: String = s"http://$containerIpAddress:${mappedPort(port)}/"
    }
    object NginxContainer {

      case class Def(port: Int) extends GenericContainer.Def[NginxContainer](
        new NginxContainer(port, GenericContainer(
          dockerImage = "nginx:latest",
          exposedPorts = Seq(port),
          waitStrategy = Wait.forHttp("/")
        ))
      )
    }
    ```
* exposing ports
    * container perspective
        * example
            ```
            GenericContainer(
                      dockerImage = "nginx:latest",
                      exposedPorts = Seq(port) // port needs to be explicitly exposed
                    )
            ```
    * host's perspective
        * Testcontainers actually exposes `exposedPorts` on a random free port
            * motivation: avoid port collisions
                * example: parallel test
            * to get actual mapped port at runtime
                * `container.mappedPort(exposedPort)`
            * to get host of container
                * `String ipAddress = container.host()`
* executing a command
    * useful for software that has a command line administration tool
    * `container.execInContainer("touch", "/somefile.txt")`
* environment variables
    * `GenericContainer(...).withEnv("API_TOKEN", "foo")`
    * but usually
        * in module tests: you use defaults from the underlying `application.conf`
        * in integration-tests: you run docker-compose with appropriate env variables
* healthcheck
    * example
        ```
        GenericContainer(...)
            .waitingFor(waitStrategy) // here comes wait strategy
        ```
    * default: wait 60 seconds for the container's first mapped network port to start listening
    * strategies
        * `Wait.forHttp("/")`
            * waiting for http endpoint 200 OK
        * `Wait.forHealthcheck()`
            * if the used image supports Docker's Healthcheck feature
* depending on another container
    ```
    GenericContainer(...).dependsOn(redis)
    ```
* accessing logs
    * standard error output
        ```
        container.getLogs(OutputFrame.OutputType.STDERR)
        ```
    * stream to logger
        ```
        Slf4jLogConsumer logConsumer = Slf4jLogConsumer(LOGGER).withSeparateOutputStreams()
        container.followOutput(logConsumer)
        ```
* https://github.com/testcontainers/testcontainers-scala
    * Scala wrapper for testcontainers-java
    * advised configuration
        * set `Test / fork := true` in `build.sbt`
            * run tests in a separate JVM from sbt
                * allows for graceful shutdown of containers once the tests have finished running
            * digression
                * by default, the run task runs in the same JVM as sbt
                    * user code can call `System.exit` => shuts down the JVM (requires restarting sbt)
* https://github.com/scottweaver/testcontainers-for-zio
    * most popular containers wrapped in ZIO

## plugins
* way to use external code in a build definition
* minimal sbt plugin is a Scala library that is built against the version of Scala that sbt runs
    * nothing special needs to be done for this type of library
* provide sbt tasks, commands, or settings
    * may provide them automatically or make them available explicitly integrate
    * example
        * codeCoverage task which would generate a test coverage report
        * sbt’s default settings are provided via three plugins:
            * CorePlugin - provides the core parallelism controls for tasks
            * IvyPlugin - provides the mechanisms to publish/resolve modules (Apache Ivy format)
            * JvmPlugin - provides the mechanisms to compile/test/run/package Java/Scala projects
* example
    1. `project/plugins.sbt`
        * `addSbtPlugin("com.github.sbt"            % "sbt-native-packager"       % "1.9.11")`
    1.  `build.sbt`
        ```
        lazy val root = (project in file("."))
          .enablePlugins(DockerPlugin) // explicitly declared plugin from sbt-native-packager
        ```
* `AutoPlugin`
    * can depend on other auto plugins and ensure these dependency settings are loaded first
    * example
        ```
        object SomePlugin extends AutoPlugin {

          override def trigger = ... // allRequirements: automatically add settings to project that has all plugins specified in requires

          override def requires = ... // example: Web && Javascript

          // contents are wildcard imported in .sbt files
          object autoImport {
            // setting definitions
            // avoid namespace clashes
            // use a plugin-specific prefix, ex. val somePluginPackageScala = taskKey[File]("Produces the scala artifact.")
          }

          import SomePlugin.autoImport._

          override def projectSettings = Seq(...)

          override def buildSettings = ... // append settings at the build-level (that is, in ThisBuild)

        }
        ```
    * triggered plugins
        * created by overriding the trigger method
        * automatically attach themselves to projects if their dependencies are met
    * autoImport
        * typically used to provide new keys (SettingKeys, TaskKeys, or InputKeys) or core methods
* list all plugins: run `plugins` on the sbt console

## 3rd party plugins
* sbt-native-packager
    * build native packages for different systems
        * example: dmg for macOS, docker, graalvm native images
    * there is a plugin for each packaging format
        * example: DockerPlugin, DebianPlugin
    * DockerPlugin
        * you can find created Dockerfile in moduleName/target/docker/Dockerfile
        * example
            ```
            lazy val moduleName = (project in file("moduleName"))
              .enablePlugins(DockerPlugin)
              .enablePlugins(AshScriptPlugin) // ash is a lightweight shell used by popular micro base Docker images
              .settings(
                name := "customer",
                libraryDependencies ++= Seq(...),
                Docker / packageName := "moduleName",
                dockerBaseImage := "openjdk:11-jre-slim-buster", // micro base Docker images
                dockerExposedPorts ++= Seq(8080),
                dockerUpdateLatest := true, // automatic update the latest tag
                dockerCommands ++= Cmd("USER", "root"), // will be appended at the end of Dockerfile
              )
            ```
        * `moduleName / Docker / publishLocal`
            * builds a Docker image using the local Docker server
* sbt-scalafmt
    * formats code so that it looks consistent
        * example: sort imports, align statements
    * configuration file: `.scalafmt.conf` (HOCON syntax)
        * example
            ```
            version    = "2.0.1"
            maxColumn  = 120
            ```
* sbt-explicit-dependencies
    * check that libraryDependencies reflects the libraries that your code depends on
    * commands
        * `undeclaredCompileDependencies`
            * shows all undeclared transitive dependencies
        * `unusedCompileDependencies`
            * shows dependencies that are not needed for compilation
                * some libraries need to be added to the runtime classpath (not needed for compilation)
                    * will be automatically excluded from consideration by this plugin
                    * these libraries should be added using the `Runtime` configuration
                        * added to (Runtime / dependencyClasspath) and not (Compile / dependencyClasspath)
    * settings
        * `unusedCompileDependenciesFilter -= moduleFilter("org.scalaz", "scalaz")`
            * filters out from the output
* sbt-scalafix
    * linting tool
    * runs after the compilation
        * firstly: statically analyses the source code to find bad patterns specified by the linter rules
        * secondly: corrects the issues it found through refactoring
    * used mainly to organize imports (`organize-imports` plugin)
        * for other linting: WartRemover (alternative to Scalafix)
    * configuration file `.scalafix.conf`
        * example
            ```
            rules = [
              MissingFinal,
              ProcedureSyntax,
              RemoveUnusedImports, // needs scalacOptions += "-Ywarn-unused"
              RemoveUnusedTerms
            ]
            ```
    * SemanticDB
        * data model for semantic information such as symbols and types about programs in Scala
        * decouples production and consumption of semantic information
        * tools like Scalafix, Metadoc and Metals don't need to know about compiler internals and can work
        with any compiler that supports SemanticDB
    * OrganizeImport
        * Scalafix semantic rule that helps you organize Scala import statements
        * to include this rule in your sbt build
            ```
            ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"
            ```
        * then configure `.scalafix.conf`
            ```
            OrganizeImports {
              groupedImports                    = Merge
              coalesceToWildcardImportThreshold = 3
              groups                            = ["re:javax?\\.", "scala.", "shop.", "*"]
              removeUnused                      = true
            }
            ```
* sbt-tpolecat
    * configures scalac options
    * used instead of defining recommended scalac flags by hand
        * example: https://tpolecat.github.io/2017/04/25/scalac-flags.html
            ```
            scalacOptions ++= Seq(
              "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
              "-encoding", "utf-8",                // Specify character encoding used by source files.
              "-explaintypes",                     // Explain type errors in more detail.
              "-feature",
              ...
            )
            ```
    * when using this plugin: don't manipulate the `scalacOptions` key directly
        * modify the `tpolecatScalacOptions` (or `tpolecatDevModeOptions`)
* sbt-wartremover
    * flexible Scala code linting tool
        * richer set of built-in rules, denoted warts
    * configuration: `sbt.build`
        ```
        .settings(
          ...
          wartremoverErrors ++= warts
        )
        ```
    * example
        ```
        5 == "5" // compiles: Any type provides an == method which is not type-safe
        ```
        with we get compilation error (solution: use cat's `===`)
        ```
        [wartremover:Equals] == is disabled - use === or equivalent instead
        [error]     a = 5 == "b"
        [error]           ^
        ```

## compiler plugins
* vs `addSbtPlugin`
    * `addCompilerPlugin` performs multiple actions
        1. adds the JAR to the classpath (the compilation classpath only, not the runtime classpath)
        via libraryDependencies
        1. customizes `scalacOptions` to enable the plugin using `-Xplugin`
* configuration
    ```
    addCompilerPlugin("org.scala-tools.sxr" %% "sxr" % "0.3.0")
    ```
    equivalent of
    ```
    libraryDependencies ++= compilerPlugin("org.scala-tools.sxr" %% "sxr" % "0.3.0")
    ```
    and equivalent of specifying compiler plugins manually
    ```
    scalacOptions += "-Xplugin:<path-to-sxr>/sxr-0.3.0.jar"
    ```
* kind-projector
    * Scala 3 has built-in type lambda syntax and kind-projector compatible syntax
    * problem: use of type projections to implement anonymous, partially-applied types
        ```
        // partially-applied type named "IntOrA"
        type IntOrA[A] = Either[Int, A]

        // type projection implementing the same type anonymously (without a name).
        ({type L[A] = Either[Int, A]})#L
        ```
    * example
        ```
        Either[Int, +*]          // equivalent to: type R[+A] = Either[Int, A]
        ```
* better-monadic-for
    * Scala 3 natively supports the semantic changes provided by better-monadic-for under `-source:future`
    compiler flag
    * features
        * final map optimization
            ```
            for {
              x <- xs
              y <- getYs(x)
            } yield y
            ```
            without a plugin is compiled into
            ```
            xs.flatMap(x => getYs(x).map(y => y))
            ```
            with plugin into
            ```
            xs.flatMap(x => getYs(x))
            ```
        * implicits in for-comprehensions
            ```
            for {
              x <- Option(42)
              implicit0(it: ImplicitTest) <- Option(ImplicitTest("eggs"))
              _ <- Option("dummy")
              _ = "dummy"
              _ = assert(implicitly[ImplicitTest] eq it)
            } yield "ok"
            ```
        * destructuring Either, etc
            ```
            def getCounts: Either[String, (Int, Int)] = Right(1,1)

            for {
              (x, y) <- getCounts
            } yield x + y
            ```
            without a plugin - compilation error
            ```
            value withFilter is not a member of Either[String,(Int, Int)]
            [error]       (x, y) <- getCounts
            [error]                 ^
            ```
            with plugin - compiles to
            ```
            getCounts
              .map(_ match { case (x, y) => x + y })
            ```
* semanticdb-scalac
    * compiler plugin that generates SemanticDB on compile
    * injects itself immediately after the typer phase of the Scala compiler and then harvests and dumps semantic
    information from Scalac in SemanticDB format
* cross compilation
    * prerequisite
        * scala full version: 2.12.12
            * useful for things that access the underlying compiler API which doesn't retain binary compatibility
            * example
                * compiler plugins like the kind projector
        * scala binary version: 2.12
            * useful because libraries compiled using a different but binary compatible version can be used in
            your project without any problems
            * example
                * using Scala 2.13.3 you can use a library that was compiled using 2.13.0 or 2.13.4 but not
                one compiled using 2.12.12
    * `cross` method
        * equivalent
            ```
            "a" % "b" % "1.0"
            ("a" % "b" % "1.0").cross(CrossVersion.disabled)
            ```
            ```
            "a" %% "b" % "1.0"
            ("a" % "b" % "1.0").cross(CrossVersion.binary)
            ```
        * set full Scala version instead of the binary Scala version:
            ```
            ("a" % "b" % "1.0").cross(CrossVersion.full)
            ```
