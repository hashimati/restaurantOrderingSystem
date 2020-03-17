package io.hashimati.myresturantordersys.repository;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;

import io.hashimati.myresturantordersys.config.ZonedDateTimeCodec;
import io.hashimati.myresturantordersys.domains.A;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.internal.CodecRegistryHelper;

import javax.inject.Singleton;

@Singleton
public class ARepository {

    private final MongoClient mongoClient;
    public ARepository(MongoClient mongoClient){

        this.mongoClient = mongoClient;

    }

    private MongoCollection<A> getCollection()
    {

        return mongoClient.getDatabase("restaurant")
                .getCollection("As", A.class);
    }
    private Single<A> findAsSingle(BsonDocument query)
    {
        return Single
                .fromPublisher(getCollection()
                        .find(query));
    }

    private Flowable<A> findAsFlowable(BsonDocument query)
    {
        return Flowable
                .fromPublisher(getCollection()
                        .find(query));
    }


    public Single<A> save(A a)
    {

        System.out.println(a);
        return Single.fromPublisher(getCollection().insertOne(a)
        ).map(success ->a);
    }

    public Flowable<A> findAll()
    {



      //  System.out.println(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        //  .getTime());
        return Flowable.fromPublisher(getCollection().find());
    }
}
