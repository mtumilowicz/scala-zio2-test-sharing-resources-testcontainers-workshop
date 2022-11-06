package api

import zio.json._

final case class OrderApiOutput(id: String)

object OrderApiOutput {
  implicit val codec: JsonCodec[OrderApiOutput] = DeriveJsonCodec.gen[OrderApiOutput]
}
