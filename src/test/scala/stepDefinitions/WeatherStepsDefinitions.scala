package stepDefinitions

import com.waioeka.sbt.runner.CucumberSpec

import scala.collection.JavaConverters._
import cucumber.api._
import cucumber.api.scala.{EN, ScalaDsl}
import io.restassured.RestAssured.given
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import io.restassured.response.{Response, ValidatableResponse}
import io.restassured.specification.RequestSpecification
import org.hamcrest.Matchers._
import org.openweathermap.test.Configuration
import org.scalatest.Matchers

class CucumberTestSuite extends CucumberSpec

class WeatherStepsDefinitions extends ScalaDsl with EN with Matchers {

  val endpoint = Configuration.baseUrl + Configuration.apiEndpoint
  val apiKey = Configuration.apiKey
  var request: RequestSpecification = _
  var response: Response = _
  var json: ValidatableResponse = _

  Given("""^a city with an name of (.*)""") { cityName: String =>
    request = given().urlEncodingEnabled(false).param("q", cityName).param("appid", apiKey)
  }

  Given("""^no credentials & a city with an name of (.*)""") { cityName: String =>
    request = given().param("q", cityName)
  }

  When("""^a user retrieve the weather info by name$""") {
    response = request.when().get(endpoint)
  }

  Then("""^status code is (\d+)$""") { statusCode: Int =>
    json = response.Then().statusCode(statusCode)
  }

  And("""^response includes the following$""") { dataTable: DataTable =>
    val expectedData = dataTable.asMaps(classOf[String], classOf[String]).asScala

    json = response.Then().body(
      "name", equalTo(expectedData.head.get("name")),
      "sys.country", equalTo(expectedData.head.get("sys.country")))
  }

  And("""^response includes the following error message (.*)""") { errorMessage: String =>

    json = response.Then().body(
      "message", equalTo(errorMessage))
  }
}
