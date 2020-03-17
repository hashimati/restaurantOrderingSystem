package io.hashimati.myresturantordersys.services;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import io.hashimati.myresturantordersys.domains.Order;
import io.hashimati.myresturantordersys.domains.OrderStatus;
import io.hashimati.myresturantordersys.domains.Session;
import io.hashimati.myresturantordersys.domains.SessionStatus;
import io.micronaut.runtime.context.scope.ThreadLocal;
import io.micronaut.scheduling.annotation.Scheduled;
import io.reactivex.Single;


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

    @Inject
    private SessionService sessionService;

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

    public Order queueOrder(Order order)
    {

        Single<Session> ssession = sessionService.getOpenSession(order.getResturantNo().substring(0,
                order.getResturantNo().lastIndexOf(
                        "_")),
                order.getResturantNo())
                .onErrorReturn(error-> new Session(){{setStatus(SessionStatus.FAILED); }});
        Session session = ssession.blockingGet();
        if(session.getStatus()== SessionStatus.FAILED)
            return Single.just(new Order(){{
                setStatus(OrderStatus.FAILED);
            }}).blockingGet();

        order.setSessionNo(session.getId());
        order.setStatus(OrderStatus.SENDING);
            order.setNumber(atomicInteger.getAndIncrement());
            concurrentLinkedQueue.add(order);
            return order; 
            

    }
    public Order dequue(){
    
        if(concurrentLinkedQueue.isEmpty())return null; 
        return (Order) concurrentLinkedQueue.poll(); 

    }
    public boolean isEmpty(){
        return concurrentLinkedQueue.isEmpty(); 
    }    
}