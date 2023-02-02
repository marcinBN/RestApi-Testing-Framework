Feature:
  Verify adding new pet via POST method

  @smoke
  Scenario: Adding new pet happy flow
    Given I create new pet category with name="Fluffy dogs" and id=456
    When I create and send new pet with name="Rudolph IV" and status="available" and id=178
    Then I "should" see pet id=178 in response body

  @smoke
  Scenario: Retrieving newly created pet
    Given I create new pet category with name="Chubby dogs" and id=567
    When I create and send new pet with name="Pinky" and status="available" and id=557
    And I make request for retrieving data of a single pet with id=557
    Then I should see pet details
        | petID | petName | petStatus |
        | 557   | Pinky   | available |

  Scenario: Using status filter positive scenario
    Given I request a list of pets with status="sold"
    Then I should get only pets with status="sold"

  Scenario: Using status filter negative scenario
    Given I request a list of pets with status="wrong status"
    Then I should get an empty list

