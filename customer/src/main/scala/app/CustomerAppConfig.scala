package app

import eu.timepit.refined.types.net.PortNumber
import zio.config.magnolia.descriptor
import zio.config.refined._
import zio.config.typesafe.TypesafeConfigSource
import zio.config.{ PropertyTreePath, ReadError, toKebabCase }
import zio.{ Layer, ZLayer }

case class OrderServiceConfig(
    host: String,
    port: PortNumber,
)

case class CustomerAppConfig(
    port: PortNumber,
    orderServiceConfig: OrderServiceConfig,
)

object CustomerAppConfig {

  implicit val orderServiceConfig = descriptor[OrderServiceConfig]
  implicit val customerAppConfig  = descriptor[CustomerAppConfig]

  val live: Layer[ReadError[String], CustomerAppConfig] =
    ZLayer {
      zio.config.read {
        descriptor[CustomerAppConfig]
          .mapKey(keyMapper)
          .from(
            TypesafeConfigSource.fromResourcePath
              .at(PropertyTreePath.$(keyMapper("CustomerAppConfig")))
          )
      }
    }

  private lazy val keyMapper = toKebabCase
}
