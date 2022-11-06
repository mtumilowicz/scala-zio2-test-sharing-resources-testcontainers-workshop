package app

import api.OrderApiOutput
import eu.timepit.refined.auto._
import zhttp.http._
import zio._
import zio.json._

object OrderHttpFacade {

  val endpoints: Http[OrderService, Nothing, Request, Response] = Http.collectZIO[Request] {
    case Method.GET -> !! / "orders" =>
      for {
        order <- ZIO.serviceWithZIO[OrderService](_.getOrder())
      } yield Response.json(toApiOutput(order).toJson)
  }

  private def toApiOutput(order: Order): OrderApiOutput = OrderApiOutput(order.id)

}
