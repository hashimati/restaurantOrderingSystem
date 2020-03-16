Feature: karate 'hello world' example
Scenario: create and retrieve a cat

Given url 'http://localhost:8080/karate'
When method get
Then status 200
And match response == "Hello World"



Scenario: create and retrieve a cat

Given url 'http://localhost:8080/karate'
When method get
Then status 200
And match response == "Hello"