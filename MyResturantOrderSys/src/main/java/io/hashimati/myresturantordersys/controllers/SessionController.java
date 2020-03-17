package io.hashimati.myresturantordersys.controllers;

import javax.inject.Inject;

import io.hashimati.myresturantordersys.domains.Session;
import io.hashimati.myresturantordersys.domains.SessionStatus;
import io.hashimati.myresturantordersys.services.SessionService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.security.authentication.Authentication;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * SessionController
 */

@Controller("/api/session")
public class SessionController {

    @Inject
    private SessionService sessionService; 
 
 
    @Post("/save")
    public Single<Session> save(@Body Session session, Authentication authentication)
    {
            if(!authentication.getName().equals(session.getUsername()))
            {
               return Single.just(new Session()
                {
                    {
                        setStatus(SessionStatus.FAILED);
                    }
                }); 
            }

            if(getOpenSession(session.getUsername(), session.getRestaurant()).blockingGet().getStatus() == SessionStatus.FAILED){

                return sessionService.save(session); 
            }
            else 
                return Single.just(new Session(){{
                    setStatus(SessionStatus.FAILED);
                }}); 
            //to open new session. 
            
    }



    /**
     * @ 
     * */
    @Put("/closesession")
    public Single<String> closeSessions(@Body Session session,  Authentication authentication)
    {
        if(!authentication.getName().equals(session.getUsername()))
        {
            return Single.just("Failed"); 

        }
        Session openSession = getOpenSession(session.getUsername(), session.getRestaurant()).blockingGet();
        

        if(openSession.getStatus()== SessionStatus.FAILED)
        {
            return Single.just("Failed"); 
        }
        else 
            return sessionService.closeSession(openSession);

    }

    
    @Get("/getOpenSession/{username}/{restaurant}")
    public Single<Session> getOpenSession(@PathVariable("username") String username, @PathVariable("restaurant") String restaurant){

            return sessionService.getOpenSession(username, restaurant)
            .onErrorReturn(error-> new Session(){{setStatus(SessionStatus.FAILED); }}); 
    }

    @Get("/getOpenSession/{city}")
    public Flowable<Session> getOpenSessionInCity(@PathVariable("city") String city)
    {
        return sessionService.getOpenSessionInCity(city, SessionStatus.OPEN);   
    }
}