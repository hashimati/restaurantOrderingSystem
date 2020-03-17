package io.hashimati.myresturantordersys.controllers;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.hashimati.myresturantordersys.domains.A;
import io.hashimati.myresturantordersys.domains.security.User;
import io.hashimati.myresturantordersys.repository.ARepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.inject.Inject;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@Controller("/A")
public class AController {



    @Inject
    private ARepository aRepository;


    @Post("/save")
    public Single<A> save(A a)
    {
        return aRepository.save(a);
    }
    @Get("/getAll")
    public Flowable<A> findAll()
    {
        LocalDate d = aRepository.findAll().blockingFirst().getLocalDate();
      System.out.println(  aRepository.findAll().blockingFirst());
      System.out.println(d.getYear());
       return aRepository.findAll();
    }
}