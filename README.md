# Framework for RestAPI testing
###### Based on Java, RestAssured and Cucumber




To do:
- [x] Core framework functionalities
- [ ] Allure reporting
- [ ] More examples of tests


## FEATURES
+ Separation of tests, test data and generic API handling methods
```java
    public String Authenticate(Object body) {
        requestSpecBuilder.setBody(body);
        return ExecuteAPI().getBody().jsonPath().get("access_token");
    }

    public ResponseOptions<Response> executeWithQueryParams(Map<String, String> queryParams) {
        requestSpecBuilder.addQueryParams(queryParams);
        return ExecuteAPI();

    }
```

+ Cucumber-style scenarios
```java
  @smoke
  Scenario: Retrieving newly created pet
    Given I create new pet category with name="Chubby dogs" and id=567
    When I create and send new pet with name="Pinky" and status="available" and id=557
    And I make request for retrieving data of a single pet with id=557
    Then I should see pet details
        | petID | petName | petStatus |
        | 557   | Pinky   | available |
```

+ Defining different environments
```java
STAGING=https://swaggerpetstore.przyklady.javastart.pl/v2
```

+ Simple reporting
```java
4 Scenarios (4 passed)
11 Steps (11 passed)
0m3.115s
```


