package io.hashimati.myresturantordersys.services;

import javax.inject.Inject;

import io.hashimati.myresturantordersys.domains.*;
import io.hashimati.myresturantordersys.repository.OrderRepository;
import io.micronaut.runtime.context.scope.ThreadLocal;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * OrderConsumer
 */
@ThreadLocal
public class OrderConsumer {

    @Inject
    private OrderProducer orderProducer; 

    @Inject
    private OrderRepository orderRespository;


    public Single<Order> consume(){
    
        if(orderProducer.isEmpty()) return null; 
            Order order = orderProducer.dequue();

       long count = orderRespository.countBySessionNo(order.getSessionNo()).blockingGet().longValue();

        order.setId(order.getSessionNo() + "_" + (++count));
        order.setCreationStatus(CreationStatus.CREATED);
        order.setStatus(OrderStatus.NOTASSIGNED);
        order.setNumber(++count);

        return orderRespository.save(order);
    }
    public void hello()
    {
    }
}