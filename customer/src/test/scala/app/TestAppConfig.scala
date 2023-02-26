package app

import eu.timepit.refined.auto._
import eu.timepit.refined.types.all.PortNumber
import testcontainers.OrderContainer
import zio.{ URLayer, ZIO, ZLayer }

object TestAppConfig {

  val live: URLayer[OrderContainer, CustomerAppConfig] = ZLayer.fromZIO {
    for {
      orderTestContainer <- ZIO.service[OrderContainer]
      orderServiceConfig = createOrderServiceConfig(orderTestContainer)
    } yield CustomerAppConfig(1, orderServiceConfig)
  }
  private def createOrderServiceConfig(orderTestContainer: OrderContainer): OrderServiceConfig =
    OrderServiceConfig(
      "http://" + orderTestContainer.host,
      PortNumber.unsafeFrom(orderTestContainer.externalPort),
    )

}
