@Posts @smoke
Feature: Get posts information

  Scenario: Get a successful response when asking for posts.
    Given The user has access to the JsonPlaceholder endpoint.
    When The user performs a get request to obtain all the posts.
    Then The user expects the correct status code.
    And The response returns the correct count.

  Scenario: Get a successful response when asking for posts by Id.
    Given The user has access to the JsonPlaceholder endpoint.
    When The user performs a get request to obtain a post by Id.
    Then The user expects the correct status code.
    And The user expects the correct user id.
    And The user expects the correct title.
    And The user expects the correct body.
    And The user expects the correct id.

  Scenario: Get a successful response when creating a new post.
    Given The user has access to the JsonPlaceholder endpoint.
    When The user performs a post request to create a new post.
    Then The user expects the correct status code.
    And The user expects the correct user id.
    And The user expects the correct title.
    And The user expects the correct body.
    And The user expects the correct id.

    Scenario: Get a successful response when updating a post.
      Given The user has access to the JsonPlaceholder endpoint.
      When The user performs a put request to update a post.
      Then The user expects the correct status code.
      And The user expects the correct user id.
      And The user expects the correct title.
      And The user expects the correct body.
      And The user expects the correct id.

    Scenario: Get a successful response when updating partially a post.
      Given The user has access to the JsonPlaceholder endpoint.
      When The user performs a patch request to update a post.
      Then The user expects the correct status code.
      And The user expects the correct user id.
      And The user expects the correct title.
      And The user expects the correct body.
      And The user expects the correct id.

    Scenario: Get a successful response when deleting a post.
      Given The user has access to the JsonPlaceholder endpoint.
      When The user performs a delete request to delete a post.
      Then The user expects the correct status code.

    Scenario: Get a successful response when getting comment by postId.
      Given The user has access to the JsonPlaceholder endpoint.
      When The user performs a get request to obtain a comment by postId.
      Then The user expects the correct status code.
      And The user expects the correct comment count.

    Scenario: Get a successful response when getting post by userId.
      Given The user has access to the JsonPlaceholder endpoint.
      When The user performs a get request to obtain all the posts by userId.
      Then The user expects the correct status code.
      And The response returns the correct count.

      Scenario: Get a successful response when getting post by title
        Given The user has access to the JsonPlaceholder endpoint.
        When The user performs a get request to obtain all the posts by title.
        Then The user expects the correct status code.
        And The response returns the correct count.
