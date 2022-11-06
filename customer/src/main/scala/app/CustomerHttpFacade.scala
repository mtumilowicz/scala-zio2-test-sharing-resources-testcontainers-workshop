package app

import zhttp.http._
import zio._
import zio.json.EncoderOps

object CustomerHttpFacade {
  val endpoints: Http[Scope with OrderService, Throwable, Request, Response] = Http.collectZIO[Request] {
    case Method.GET -> !! / "customers" =>
      for {
        orderService <- ZIO.service[OrderService]
        order        <- orderService.getOrder
        apiOutput = CustomerApiOutput("customerId", order)
      } yield Response.json(apiOutput.toJson)
  }
}
