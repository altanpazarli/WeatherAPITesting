package org.openweathermap.test

import com.typesafe.config.ConfigFactory

object Configuration {

  val config = ConfigFactory.load()

  val baseUrl = config.getString("common.baseUrl")

  val apiEndpoint = config.getString("common.apiEndpoint")

  val apiKey = config.getString("common.apiKey")

}
