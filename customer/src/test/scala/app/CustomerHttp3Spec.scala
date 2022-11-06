package app

import api.OrderApiOutput
import eu.timepit.refined.auto._
import sttp.client3.httpclient.zio.HttpClientZioBackend
import testcontainers.OrderContainer
import zio._
import zio.test.Assertion._
import zio.test._

object CustomerHttp3Spec extends SharedOrderContainerSpec {
  override def spec =
    suite("CustomerHttp3Spec")(
      test("get order 1") {
        for {
          orderService <- ZIO.service[OrderService]
          response     <- orderService.getOrder
        } yield assert(response)(equalTo(OrderApiOutput("orderId")))
      },
      test("get order 2") {
        for {
          orderService <- ZIO.service[OrderService]
          response     <- orderService.getOrder
        } yield assert(response)(equalTo(OrderApiOutput("orderId")))
      },
      test("get order 3") {
        for {
          orderService <- ZIO.service[OrderService]
          response     <- orderService.getOrder
        } yield assert(response)(equalTo(OrderApiOutput("orderId")))
      },
    ).provideSome[Scope with OrderContainer](OrderService.layer, HttpClientZioBackend.layer(), testCustomerAppConfig)

  val testCustomerAppConfig = ZLayer.fromZIO {
    for {
      orderTestContainer <- ZIO.service[OrderContainer]
    } yield CustomerAppConfig(1, OrderServiceConfig.from(orderTestContainer))
  }

}
