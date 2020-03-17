package io.hashimati.myresturantordersys.services;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.hashimati.myresturantordersys.domains.Order;
import io.hashimati.myresturantordersys.domains.OrderStatus;
import io.hashimati.myresturantordersys.repository.OrderRepository;
import io.micronaut.scheduling.annotation.Scheduled;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * OrderService
 */

@Singleton
public class OrderService {
    @Inject
    private OrderConsumer orderConsumer ;


    @Inject 
    private OrderRepository orderRepository; 
  
  
    @Scheduled(initialDelay = "14s", fixedDelay = "1s" )
    public Single<Order> consume(){
         
         return orderConsumer.consume();
    }

    public Single<Boolean> deleteById(String id) {
        return orderRepository.deleteById(id);
    
    }
    public Single<Order> save(Order order)
    {

      return orderRepository.save(order); 
      
    }

    public Single<Long> countBySessionNo(String id)
    {
        return orderRepository.countBySessionNo(id);
    }

    public Flowable<Order> getOrdersByUsernameAndRestaurant(String name, String restaurant) {
        return orderRepository.getOrdersByUsernameAndRestaurant(name, restaurant);
    }
}