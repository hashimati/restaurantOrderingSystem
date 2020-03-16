package io.hashimati.myresturantordersys.repository;

import javax.inject.Singleton;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;

import org.bson.BsonDocument;
import org.bson.BsonString;

import io.hashimati.myresturantordersys.domains.Restaurant;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * ResturantRepository
 */
@Singleton
public class RestaurantRepository {


	private final MongoClient mongoClient; 
	public RestaurantRepository(MongoClient mongoClient){

		this.mongoClient = mongoClient; 
	}
	
	private MongoCollection<Restaurant> getCollection()
	{
		return mongoClient
				.getDatabase("restaurant")
				.getCollection("restaurants", Restaurant.class); 
	}
	private Single<Restaurant> findAsSingle(BsonDocument query)
    {
        return Single
        .fromPublisher(getCollection()
        .find(query)); 
    }

    private Flowable<Restaurant> findAsFlowable(BsonDocument query)
    {
        return Flowable
        .fromPublisher(getCollection()
        .find(query)); 
    }
    public Single<Restaurant> findById(String id){
        BsonDocument document = new BsonDocument(); 
        document.append("_id", new BsonString(id)); 
        return findAsSingle(document); 
    }
    public Single<Restaurant> findByRestaurant(String id){
        BsonDocument document = new BsonDocument(); 
        document.append("restaurant", new BsonString(id)); 
        return findAsSingle(document); 
    }
    public Single<Long> countByOwner(String owner)
    {
        BsonDocument query = new BsonDocument(); 
        query.append("username", new BsonString(owner)); 
       return Single.fromPublisher(getCollection().countDocuments(query)); 
        
    }

    public Single<Restaurant> save(Restaurant restaurant)
    {
        return Single.fromPublisher(getCollection().insertOne(restaurant))
        .map(success->restaurant); 
        
    }



	public Single<Boolean> deleteById(String id) {
        BsonDocument document = new BsonDocument(); 
        document.append("_id", new BsonString(id)); 
        return Single.fromPublisher(getCollection().deleteOne(document))
        .map(success->Boolean.TRUE)
        .onErrorReturn(faile->Boolean.FALSE); 
        
	}
	
}