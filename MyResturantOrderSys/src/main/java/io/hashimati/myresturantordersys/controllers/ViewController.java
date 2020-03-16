package io.hashimati.myresturantordersys.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.View;
import io.micronaut.http.MediaType;

@Controller("/")
public class ViewController {


    @View("index")
    @Get("/")
    public HttpResponse home()
    {
            return HttpResponse.ok(); 
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get("/karate")
    public String hello()
    {
        return "Hello World"; 

    }
}