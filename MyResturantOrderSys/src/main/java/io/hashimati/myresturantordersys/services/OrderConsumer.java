package io.hashimati.myresturantordersys.services;

import javax.inject.Inject;

import io.hashimati.myresturantordersys.domains.Order;
import io.hashimati.myresturantordersys.domains.OrderStatus;
import io.micronaut.runtime.context.scope.ThreadLocal;

/**
 * @author Ahmed Al Hashmi @hashimati
 * OrderConsumer
 */
@ThreadLocal
public class OrderConsumer {

    @Inject
    private OrderProducer orderProducer; 


    public Order show(){
    
        if(orderProducer.isEmpty()) return null; 
        Order order = orderProducer.dequue(); 
        
        System.out.println(order); 
        order.setStatus(OrderStatus.RECEIVED);
        return order; 

    }
    public void hello()
    {
    }
}