package io.hashimati.myresturantordersys.services;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import io.hashimati.myresturantordersys.domains.Order;
import io.micronaut.runtime.context.scope.ThreadLocal;
import io.micronaut.scheduling.annotation.Scheduled;


/**
 * @author Ahmed Al Hashmi @hashimati
 * OrderProducer
*/

@ThreadLocal
public class OrderProducer {
    @Inject
    private ConcurrentLinkedQueue concurrentLinkedQueue ;
    @Inject 
    private AtomicInteger atomicInteger; 
 
    @Scheduled(cron = "0 0 0 * * ?") 
    public void resetNumber(){
        atomicInteger.set(0);
    }
    public String done()
    {
        Order order = new Order(); 
        order.setNumber(atomicInteger.getAndIncrement());
       return concurrentLinkedQueue.add(order)?"done":"Failed"; 
    
        
    }
    public Order dequue(){
    
        if(concurrentLinkedQueue.isEmpty())return null; 
        return (Order) concurrentLinkedQueue.poll(); 

    }
    public boolean isEmpty(){
        return concurrentLinkedQueue.isEmpty(); 
    }    
}