package io.hashimati.myresturantordersys.config;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Singleton;

import io.hashimati.myresturantordersys.domains.Order;
import io.micronaut.context.annotation.Factory;

/**
 * @author Ahmed Al Hashmi @hashimati
 * OrderFactory
 */

@Factory
public class OrderFactory {

    @Singleton
    public ConcurrentLinkedQueue<Order> concurrentLinkedQueue(){
       return new ConcurrentLinkedQueue<Order>();  
    }; 


    @Singleton
    public  AtomicInteger atomicInteger(){

        AtomicInteger value = new AtomicInteger(); 
        value.set(0); 
        return value; 
    }
    
    
}