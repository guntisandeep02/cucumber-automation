Feature: Search Logo tests
  As a customer, I want to search and the logo of J.P. Morgan from application welcome page

  @Test1
  Scenario Outline: View search results from Search List
    Given Google Search page is displayed
    When Input data with "<SearchValue>" in search bar
    Then Verify the "<SearchValue>" data is displayed in the search list
    Examples:
      | SearchValue   |
      | J. P. Morgan |

  @Test2
  Scenario Outline: Verify page is navigated to Welcome page of J.P.Morgan and Logo is available
    Given Google Search page is displayed
    When Input data with "<SearchValue>" in search bar
    And  Click on first result from the result set list
    And Click on first link from results page
    Then Verify the page is navigated to application welcome page
    Then Verify the Logo in Welcome page of J.P.Morgan
    Examples:
      | SearchValue   |
      | J. P. Morgan |
