package io.hashimati.myresturantordersys.controllers;

import javax.inject.Inject;

import io.hashimati.myresturantordersys.domains.security.User;
import io.hashimati.myresturantordersys.services.UserService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.reactivex.Single;
/**
 * @author Ahmed Al Hashmi @hashimati
 * UserController
 */
@Controller("/api/users")
public class UserController {
  
  
    @Inject
    private UserService userService ;

    @Post("/register/{role}")
    public Single<String> registerUser(@Body User user, @PathVariable("role") String role)
    {
        user.getRoles().add(role); 
        return userService.save(user); 
    }
}