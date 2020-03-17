package io.hashimati.myresturantordersys.controllers;

import javax.inject.Inject;

import io.hashimati.myresturantordersys.domains.CreationStatus;
import io.hashimati.myresturantordersys.domains.Order;
import io.hashimati.myresturantordersys.services.OrderProducer;
import io.hashimati.myresturantordersys.services.OrderService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.authentication.Authentication;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * OrderController
 */
@Controller("/api/orders")
public class OrderController {

    @Inject
    private OrderService orderService ;
   
    @Inject
    private OrderProducer orderProducer; 


    @Post("/save")
    public Single<Order> save(@Body Order order, Authentication authentication)
    {
        if(authentication.getName().equals(order.getUsername()))
             return Single.just(orderProducer.queueOrder(order));
        return Single.just(new Order(){{
            setCreationStatus(CreationStatus.FAILED);
        }});
    }
    @Get("/getorders/{restaurant}")
    public Flowable<Order> getOrders(@PathVariable("restaurant") String restaurant, Authentication authentication)
    {
            return orderService.getOrdersByUsernameAndRestaurant(authentication.getName(), restaurant);

    }
}