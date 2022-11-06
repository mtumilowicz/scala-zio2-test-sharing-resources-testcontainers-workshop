import sbt._

object SbtCommandAliases {

  val fix = addCommandAlias("fix", "importFix;fmtAll")

  val fmtAll =
    addCommandAlias("fmtAll", "scalafmtAll;scalafmtSbt")

  val importFix =
    addCommandAlias("importFix", "scalafixAll --rules OrganizeImports")

  val unusedDeps =
    addCommandAlias("unusedDeps", "unusedCompileDependencies")

  val transitiveDeps =
    addCommandAlias("transitiveDeps", "undeclaredCompileDependencies")

  val publishLocalAll =
    addCommandAlias("publishLocalAll", publishLocalAllCmd)

  private def publishLocal(moduleName: String): String =
    s"$moduleName / Docker / publishLocal"

  private lazy val modules            = List("order", "customer")
  private lazy val publishLocalAllCmd = modules.map(publishLocal).mkString(";")

}
