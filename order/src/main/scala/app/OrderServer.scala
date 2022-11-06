package app

import cats.implicits.showInterpolator
import eu.timepit.refined.auto._
import eu.timepit.refined.cats._
import zhttp.service.Server
import zio._

object OrderServer extends ZIOAppDefault {

  val program: ZIO[Any, Throwable, ExitCode] = (for {
    config <- ZIO.service[OrderAppConfig]
    _      <- Console.printLine(show"Starting server on http://localhost:${config.port}")
    _      <- Server.start(config.port, OrderHttpFacade.endpoints)
  } yield ExitCode.success)
    .provide(OrderService.layer, OrderAppConfig.layer)

  override def run: ZIO[ZIOAppArgs with Scope, Any, Any] = program
}
