package io.hashimati.myresturantordersys.services;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.hashimati.myresturantordersys.domains.Restaurant;
import io.hashimati.myresturantordersys.domains.Session;
import io.hashimati.myresturantordersys.domains.SessionStatus;
import io.hashimati.myresturantordersys.repository.RestaurantRepository;
import io.hashimati.myresturantordersys.repository.SessionRepository;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * SessionService
 */
 @Singleton
public class SessionService {



    @Inject
    private SessionRepository sessionRepository; 
    @Inject
    private RestaurantService restaurantService; 

    public Single<Session> save(Session session)
    {
        
        Restaurant restaurant = restaurantService.getById(session.getRestaurant()).blockingGet();

        session.setCity(restaurant.getCity()); 
        long count = sessionRepository.countByRestaurant(session.getRestaurant()).blockingGet().longValue()  ; 
        
        session.setId(
                session.getRestaurant()+"_" + (++count
                ));
                session.setOpeningTime(new Date()); 
        return sessionRepository.save(session); 
    }
	public Single<String> setRestaurantStatus(String restaurant, SessionStatus close) {
        
        return Single.just(sessionRepository.changeSessionStatusByRestaurantAndStatus(restaurant, close).blockingGet().getStatus().toString()) ;
        }


	public Single<String> closeSession(Session session) {
		return sessionRepository.closeSession(session); 
	}


     
        public Single<Session> getOpenSession(String username, String restaurant){
    
                return sessionRepository.getSessionByRestaurantAndStatus(restaurant, SessionStatus.OPEN); 
        }


	public Flowable<Session> getOpenSessionInCity(String city, SessionStatus status) {
	        return sessionRepository.getSessionByCityAndStatus(city, status);
	}
    
}