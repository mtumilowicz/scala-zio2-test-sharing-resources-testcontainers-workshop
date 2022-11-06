package app

import api.OrderApiOutput
import sttp.capabilities.WebSockets
import sttp.capabilities.zio.ZioStreams
import sttp.client3.ziojson._
import sttp.client3.{ SttpBackend, UriContext, basicRequest }
import zio._

case class OrderService(config: OrderServiceConfig, httpClient: SttpBackend[Task, ZioStreams with WebSockets]) {

  def getOrder: ZIO[Scope, Throwable, OrderApiOutput] = {
    val localhostRequest = basicRequest
      .get(uri"${config.host}:${config.port.value}/orders")
      .response(asJson[OrderApiOutput])

    for {
      response <- localhostRequest.send(httpClient)
      body     <- ZIO.fromEither(response.body)
    } yield body
  }

}

object OrderService {

  type Env = CustomerAppConfig with SttpBackend[Task, ZioStreams with WebSockets]

  val layer: ZLayer[Env, Throwable, OrderService] =
    ZLayer.fromZIO {
      ZIO.scoped {
        for {
          orderServiceConfig <- ZIO.serviceWith[CustomerAppConfig](_.orderServiceConfig)
          httpClient         <- ZIO.service[SttpBackend[Task, ZioStreams with WebSockets]]
        } yield OrderService(orderServiceConfig, httpClient)
      }
    }
}
