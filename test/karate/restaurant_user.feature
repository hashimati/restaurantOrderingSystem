Feature: Testing Restaurant User
Background:
    * def rootURL = 'http://localhost:8080/api/'
    * def registrationPath = 'users/register/restaurant'
    * def user = {id: 'hashimatiRest', password:'helloworld'}


Scenario: Restaurant User Registration
Given url rootURL.concat(registrationPath)
And request user
When method post
Then status 200
And assert (response == 'Success' || response == 'Failed')

Scenario: Restaurant User Login
Given url 'http://localhost:8080/login'
And request {username: 'hashimatiRest', password:'helloworld'}
When method post
Then status 200 
And match response == {username:'hashimatiRest' ,'roles':['restaurant'],access_token:'#notnull',refresh_token:'#notnull',token_type:"Bearer", expires_in:3600}


Scenario: create restaurant
Given url 'http://localhost:8080/login'
And request {username: 'hashimatiRest', password:'helloworld'}
When method post
Then status 200 
And match response == {username:'hashimatiRest' ,'roles':['restaurant'],access_token:'#notnull',refresh_token:'#notnull',token_type:"Bearer", expires_in:3600}
And def accessToken = response.access_token

Given url 'http://localhost:8080/api/restaurant/save'
And header Authorization = 'Bearer ' + accessToken
And request {username:'hashimatiRest', name:'Happiness', city:'Saihat', country:'Saihat', address:'Saihat' }
When method post
Then status 200
And def restaurantId = response.id

# Given url 'http://localhost:8080/api/menu/save'
# And header Authorization = 'Bearer ' + accessToken
# And request {id: '#(restaurantId)', username:'hashimatiRest', restaurant:'#(restaurantId)'}
# When method post
# Then status 200

# Given url 'http://localhost:8080/api/menu/hashimatiRest/hashimatiRest_1/addItem'
# And header Authorization = 'Bearer ' + accessToken
# And request {itemName: 'Chicken Shawerma', description:'Gr illed dChicken, Pickle, Tomato,....', price:10}
# When method post
# Then status 200

Given url 'http://localhost:8080/api/menu/uploadMenu/hashimatiRest/hashimatiRest_1'
And header Authorization = 'Bearer ' + accessToken
And multipart field file = read('C:/Users/ahmed/Desktop/Menu.xlsx')
And multipart field message = 'hello world'
When method post
Then status 200

Given url 'http://localhost:8080/api/session/save'
And header Authorization = 'Bearer '+ accessToken
And request { username: 'hashimatiRest', restaurant:'hashimatiRest_1'}
When method post
Then status 200

Given url 'http://localhost:8080/api/session/closesession'
And header Authorization = 'Bearer '+ accessToken
And request { username: 'hashimatiRest', restaurant:'hashimatiRest_1'}
When method put
Then status 200