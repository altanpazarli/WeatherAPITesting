Feature: Get weather by city name

  Scenario: User calls a web service to get a weather info by its name
    Given a city with an name of Samsun
    When a user retrieve the weather info by name
    Then status code is 200
    And response includes the following
      | name   | sys.country |
      | Samsun | TR          |

  Scenario: User calls a web service to get a weather info by wrong name
    Given a city with an name of doesNotExistCity
    When a user retrieve the weather info by name
    Then status code is 404
    And response includes the following error message city not found

  Scenario: User calls a web service to get a weather info by bad request
    Given a city with an name of &
    When a user retrieve the weather info by name
    Then status code is 400
    And response includes the following error message Nothing to geocode

  Scenario: User calls a web service to get a weather info without credentials
    Given no credentials & a city with an name of Zaragoza
    When a user retrieve the weather info by name
    Then status code is 401
    And response includes the following error message Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.