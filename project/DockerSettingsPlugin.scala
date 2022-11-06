import com.typesafe.sbt.packager.Keys.{ dockerBaseImage, dockerExposedPorts, dockerUpdateLatest, packageName }
import com.typesafe.sbt.packager.docker.DockerPlugin
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport.Docker
import sbt.{ AutoPlugin, settingKey }

object DockerSettingsPlugin extends AutoPlugin {

  override def requires = DockerPlugin

  object autoImport {
    val dockerImageName   = settingKey[String]("equivalent of Docker / packageName")
    val dockerExposedPort = settingKey[Int]("equivalent of dockerExposedPorts but with only one port")
  }

  import DockerSettingsPlugin.autoImport._

  override def projectSettings = Seq(
    Docker / packageName := dockerImageName.value,
    dockerBaseImage := "openjdk:11-jre-slim-buster",
    dockerExposedPorts += dockerExposedPort.value,
    dockerUpdateLatest := true,
  )

}
