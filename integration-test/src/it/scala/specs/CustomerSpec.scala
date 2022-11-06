package specs

import io.circe.Json
import io.circe.literal.JsonStringContext
import io.circe.parser._
import it.IntegrationTestConfig
import sttp.capabilities.WebSockets
import sttp.capabilities.zio.ZioStreams
import sttp.client3.{ asStringAlways, _ }
import sttp.client3.httpclient.zio.HttpClientZioBackend
import zio.test.Assertion.equalTo
import zio.test._
import zio.{ Scope, Task, ZIO }

object CustomerSpec extends ZIOSpecDefault {
  override def spec: Spec[TestEnvironment with Scope, Any] =
    suite("CustomerSpec")(
      test("get ") {
        val expectedJson =
          json"""
            {
              "customerId": "customerId",
              "order": {
                "id": "orderId"
              }
            }
              """
        for {
          client   <- ZIO.service[SttpBackend[Task, ZioStreams with WebSockets]]
          response <- getCustomers(client)
        } yield assert(asJson(response))(equalTo(expectedJson))
      },
    ).provide(IntegrationTestConfig.layer, HttpClientZioBackend.layer())

  private def asJson(response: String): Json = parse(response).getOrElse(Json.Null)

  private def getCustomers(httpClient: SttpBackend[Task, ZioStreams with WebSockets]) = {

    for {
      config <- ZIO.service[IntegrationTestConfig]
      customerServiceConfig = config.customerServiceConfig
      localhostRequest = basicRequest
        .get(uri"${customerServiceConfig.host}:${customerServiceConfig.port}/customers")
        .response(asStringAlways)
      response <- localhostRequest.send(httpClient)
    } yield response.body
  }
}
