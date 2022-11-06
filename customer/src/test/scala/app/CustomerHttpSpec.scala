package app

import api.OrderApiOutput
import eu.timepit.refined.auto._
import sttp.client3.httpclient.zio.HttpClientZioBackend
import testcontainers.OrderContainer
import zio._
import zio.test.Assertion._
import zio.test._

object CustomerHttpSpec extends ZIOSpecDefault {
  override def spec =
    suite("a")(
      test("b") {
        for {
          orderService <- ZIO.service[OrderService]
          response     <- orderService.getOrder
        } yield assert(response)(equalTo(OrderApiOutput("orderId")))
      },
      test("c") {
        for {
          orderService <- ZIO.service[OrderService]
          response     <- orderService.getOrder
        } yield assert(response)(equalTo(OrderApiOutput("orderId")))
      },
      test("d") {
        for {
          orderService <- ZIO.service[OrderService]
          response     <- orderService.getOrder
        } yield assert(response)(equalTo(OrderApiOutput("orderId")))
      },
      test("e") {
        def getCounts: UIO[(Int, Int)] = ZIO.succeed((1, 3))
        for {
          (x, y) <- getCounts
        } yield assert(x + y)(equalTo(4))
      },
    ).provideSome[Scope with CustomerAppConfig](OrderService.layer, HttpClientZioBackend.layer())
      .provideSomeShared(OrderContainer.live, testCustomerAppConfig)

  val testCustomerAppConfig = ZLayer.fromZIO {
    for {
      orderTestContainer <- ZIO.service[OrderContainer]
    } yield CustomerAppConfig(1, OrderServiceConfig.from(orderTestContainer))
  }

}
