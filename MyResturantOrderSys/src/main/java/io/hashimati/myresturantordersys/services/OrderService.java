package io.hashimati.myresturantordersys.services;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.hashimati.myresturantordersys.domains.Order;
import io.hashimati.myresturantordersys.repository.OrderRepository;
import io.micronaut.scheduling.annotation.Scheduled;
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
    public Order consume()
    {
        
        Order order = orderConsumer.show(); 
         
         return order; 
    }

    public Single<Boolean> deleteById(String id) {
		return orderRepository.deleteById(id);
	}
   
}