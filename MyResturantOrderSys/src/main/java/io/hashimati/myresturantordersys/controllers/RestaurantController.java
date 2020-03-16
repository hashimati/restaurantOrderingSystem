package io.hashimati.myresturantordersys.controllers;

import javax.inject.Inject;

import io.hashimati.myresturantordersys.domains.Menu;
import io.hashimati.myresturantordersys.domains.Restaurant;
import io.hashimati.myresturantordersys.services.RestaurantService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.server.multipart.MultipartBody;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * ResturantController
 */
 @Controller("/api/restaurant")
public class RestaurantController {



    @Inject
   private RestaurantService restaurantService; 


   @Get("/sayHi")
   public String sayHi()
   {
       return "hello World"; 

   }

   @Post("/save")
    public Single<Restaurant> save(Restaurant restaurant)
    {
       return restaurantService.save(restaurant);
    }

    @Get("/{id}")
    public Single<Restaurant> getRestaurant(@PathVariable("id") String id)
    {
        return restaurantService.getById(id); 
    }
    @Delete("{id}")
    public Single<Boolean> deleteRestaurant(@PathVariable("id") String id)
    {
        return restaurantService.deleteById(id); 
    }
    





}