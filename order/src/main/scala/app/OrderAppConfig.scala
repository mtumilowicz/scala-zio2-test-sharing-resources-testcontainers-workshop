package app

import eu.timepit.refined.types.net.PortNumber
import zio.config.magnolia.descriptor
import zio.config.refined._
import zio.config.typesafe.TypesafeConfigSource
import zio.config.{ PropertyTreePath, ReadError, toKebabCase }
import zio.{ Layer, ZLayer }

case class OrderAppConfig(port: PortNumber)

object OrderAppConfig {

  implicit val orderAppConfig = descriptor[OrderAppConfig]

  val layer: Layer[ReadError[String], OrderAppConfig] =
    ZLayer {
      zio.config.read {
        descriptor[OrderAppConfig]
          .mapKey(keyMapper)
          .from(
            TypesafeConfigSource.fromResourcePath
              .at(PropertyTreePath.$(keyMapper("OrderAppConfig")))
          )
      }
    }

  private lazy val keyMapper = toKebabCase
}
