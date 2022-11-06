import com.typesafe.sbt.packager.docker.Cmd

object DockerCommands {

  val installCurl = Seq(
    userRoot,
    Cmd("RUN", "apt-get update -y && apt-get install -y curl && rm -rf /var/lib/apt/lists/*")
  )

  private lazy val userRoot = Cmd("USER", "root")

}
