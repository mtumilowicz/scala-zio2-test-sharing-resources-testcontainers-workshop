package app

import cats.implicits.showInterpolator
import eu.timepit.refined.auto._
import eu.timepit.refined.cats._
import sttp.client3.httpclient.zio.HttpClientZioBackend
import sttp.client3.ziojson._
import zhttp.service.Server
import zio.{ ZIO, ZIOAppDefault, _ }

object CustomerServer extends ZIOAppDefault {
  val program: ZIO[Scope, Throwable, ExitCode] = (for {
    config <- ZIO.service[CustomerAppConfig]
    _      <- Console.printLine(show"Starting server on http://localhost:${config.port}")
    _      <- Server.start(config.port, CustomerHttpFacade.endpoints)
  } yield ExitCode.success)
    .provideSome(
      CustomerAppConfig.live,
      OrderService.layer,
      HttpClientZioBackend.layer(),
    )

  override def run: ZIO[Scope with ZIOAppArgs with Scope, Any, Any] = program
}
