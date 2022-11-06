package testcontainers

import com.dimafeng.testcontainers.GenericContainer
import zio._

case class OrderContainer(port: Int, underlying: GenericContainer) extends GenericContainer(underlying) {

  lazy val externalPort = mappedPort(port)

}

object OrderContainer {
  private val imageName   = "order:latest"
  private val exposedPort = 8080

  case object Def
      extends GenericContainer.Def[OrderContainer](
        new OrderContainer(
          exposedPort,
          GenericContainer(
            dockerImage = imageName,
            exposedPorts = Seq(exposedPort),
          ),
        ),
      )

  val live: ZLayer[Scope, Throwable, OrderContainer] = ZLayer.fromZIO {
    ZIO
      .succeed(OrderContainer.Def)
      .flatMap(
        container =>
          ZIO.acquireRelease(ZIO.attemptBlocking(container.start()))(a => ZIO.attemptBlocking(a.stop()).orDie)
      )
  }
}
