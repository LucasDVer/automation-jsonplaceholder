@Posts @smoke
Feature: Get location information of a city using country and postal code

  Scenario Outline: Get a successful response when sending a valid postal code and a valid country
    Given The user has access to the JsonPlaceholder endpoint.
    When The user performs a get request to obtain all the posts.
    Then The user expects the correct status code after doing a <requestName> request.
    And The user expects the correct count of posts after doing a <requestName> request.

    Examples:
      | requestName   |
      | "getAll"      |