package app

import eu.timepit.refined.auto._
import eu.timepit.refined.types.string.NonEmptyString
import zio.{ ZIO, ZLayer }

case class Order(id: NonEmptyString)

class OrderService {

  def getOrder(): ZIO[Any, Nothing, Order] = ZIO.succeed(Order("orderId"))

}

object OrderService {
  val layer = ZLayer.succeed(new OrderService)
}
