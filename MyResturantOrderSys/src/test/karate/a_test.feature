Feature: Testing A

Scenario: Restaurant User Registration
Given url 'http://localhost:8080/A/save'
And request {x:23}
When method post
Then status 200