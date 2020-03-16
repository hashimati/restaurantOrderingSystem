package io.hashimati.myresturantordersys.controllers;

import javax.inject.Inject;

import io.hashimati.myresturantordersys.domains.Order;
import io.hashimati.myresturantordersys.services.OrderService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

/**
 * @author Ahmed Al Hashmi @hashimati
 * OrderController
 */
@Controller("/api/orders")
public class OrderController {

    @Inject
    private OrderService orderService ;
   
    @Get("/se")
    public Order show(){
    
         return orderService.consume();

    }
}