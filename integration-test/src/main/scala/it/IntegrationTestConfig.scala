package it

import eu.timepit.refined.types.all.PortNumber
import zio._
import zio.config.magnolia.descriptor
import zio.config.refined._
import zio.config.typesafe.TypesafeConfigSource
import zio.config.{ PropertyTreePath, ReadError, toKebabCase }

case class CustomerServiceConfig(
    host: String,
    port: PortNumber,
)

case class OrderServiceConfig(
    host: String,
    port: PortNumber,
)

case class IntegrationTestConfig(
    customerServiceConfig: CustomerServiceConfig,
    orderServiceConfig: OrderServiceConfig,
)

object IntegrationTestConfig {

  implicit val orderServiceConfig    = descriptor[OrderServiceConfig]
  implicit val customerServiceConfig = descriptor[CustomerServiceConfig]

  val layer: Layer[ReadError[String], IntegrationTestConfig] =
    ZLayer {
      zio.config.read {
        descriptor[IntegrationTestConfig]
          .mapKey(keyMapper)
          .from(
            TypesafeConfigSource.fromResourcePath
              .at(PropertyTreePath.$(keyMapper("IntegrationTestConfig")))
          )
      }
    }

  private lazy val keyMapper = toKebabCase
}

object App extends ZIOAppDefault {
  override def run: ZIO[ZIOAppArgs with Scope, Any, Any] =
    (for {
      config <- ZIO.service[IntegrationTestConfig]
      _      <- zio.Console.printLine(config)
    } yield ()).provide(IntegrationTestConfig.layer)
}
