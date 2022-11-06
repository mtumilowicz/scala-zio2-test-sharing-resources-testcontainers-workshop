package app

import api.OrderApiOutput
import zio.json.{ DeriveJsonCodec, JsonCodec }

case class CustomerApiOutput(customerId: String, order: OrderApiOutput)

object CustomerApiOutput {
  implicit val codec: JsonCodec[CustomerApiOutput] = DeriveJsonCodec.gen[CustomerApiOutput]
}
