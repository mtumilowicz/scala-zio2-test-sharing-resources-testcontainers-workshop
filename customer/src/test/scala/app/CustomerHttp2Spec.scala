package app

import api.OrderApiOutput
import eu.timepit.refined.auto._
import sttp.client3.httpclient.zio.HttpClientZioBackend
import testcontainers.OrderContainer
import zhttp.http.{ Request, URL }
import zio._
import zio.test.Assertion._
import zio.test._

object CustomerHttp2Spec extends SharedOrderContainerSpec {

  val customersUrl = "/customers"
  override def spec =
    suite("CustomerHttp2Spec")(
      test("get order 1") {
        val expectedApiOutput = CustomerApiOutput("customerId", OrderApiOutput("orderId"))
        for {
          url       <- ZIO.fromEither(URL.fromString(customersUrl))
          response  <- CustomerHttpFacade.endpoints(Request(url = url))
          body      <- response.body.asString
          apiOutput <- ZIO.fromEither(CustomerApiOutput.codec.decoder.decodeJson(body))
        } yield assert(apiOutput)(equalTo(expectedApiOutput))
      },
      test("get order 2") {
        val expectedApiOutput = CustomerApiOutput("customerId", OrderApiOutput("orderId"))
        for {
          url       <- ZIO.fromEither(URL.fromString(customersUrl))
          response  <- CustomerHttpFacade.endpoints(Request(url = url))
          body      <- response.body.asString
          apiOutput <- ZIO.fromEither(CustomerApiOutput.codec.decoder.decodeJson(body))
        } yield assert(apiOutput)(equalTo(expectedApiOutput))
      },
      test("get order 3") {
        val expectedApiOutput = CustomerApiOutput("customerId", OrderApiOutput("orderId"))
        for {
          url       <- ZIO.fromEither(URL.fromString(customersUrl))
          response  <- CustomerHttpFacade.endpoints(Request(url = url))
          body      <- response.body.asString
          apiOutput <- ZIO.fromEither(CustomerApiOutput.codec.decoder.decodeJson(body))
        } yield assert(apiOutput)(equalTo(expectedApiOutput))
      },
    ).provideSome[Scope with OrderContainer](OrderService.layer, HttpClientZioBackend.layer(), TestAppConfig.live)

}
