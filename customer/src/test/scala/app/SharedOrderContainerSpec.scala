package app

import testcontainers.OrderContainer
import zio.test.ZIOSpec
import zio.{ Scope, ZLayer }

abstract class SharedOrderContainerSpec extends ZIOSpec[OrderContainer] {
  override val bootstrap: ZLayer[Scope, Throwable, OrderContainer] = OrderContainer.live
}
