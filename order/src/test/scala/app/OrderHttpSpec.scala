package app

import api.OrderApiOutput
import zhttp.http.{ Request, URL }
import zio.ZIO
import zio.test.Assertion._
import zio.test._

object OrderHttpSpec extends ZIOSpecDefault {
  override def spec =
    suite("OrderHttpSpec")(
      test("get order") {
        for {
          url            <- ZIO.fromEither(URL.fromString("/orders"))
          response       <- OrderHttpFacade.endpoints(Request(url = url))
          body           <- response.body.asString
          orderApiOutput <- ZIO.fromEither(OrderApiOutput.codec.decoder.decodeJson(body))
        } yield assert(orderApiOutput)(equalTo(OrderApiOutput("orderId")))
      },
    ).provide(OrderService.layer)
}
