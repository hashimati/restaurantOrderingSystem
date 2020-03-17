package io.hashimati.myresturantordersys.repository;

import javax.inject.Singleton;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;

import org.bson.BsonDocument;
import org.bson.BsonString;

import io.hashimati.myresturantordersys.domains.Order;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.bson.conversions.Bson;

/**
 * @author Ahmed Al Hashmi @hashimati
 * OrderRepository
 */
@Singleton
public class OrderRepository {

    private final MongoClient mongoClient; 
	public OrderRepository(final MongoClient mongoClient){

		this.mongoClient = mongoClient; 
	}
	
	private MongoCollection<Order> getCollection()
	{
		return mongoClient
				.getDatabase("restaurant")
				.getCollection("orders", Order.class); 
	}
	private Single<Order> findAsSingle(final BsonDocument query)
    {
        return Single
        .fromPublisher(getCollection()
        .find(query)); 
    }

    private Flowable<Order> findAsFlowable(final BsonDocument query)
    {
        return Flowable
        .fromPublisher(getCollection()
        .find(query)); 
    }
    public Single<Order> findById(final String id){
        final BsonDocument document = new BsonDocument(); 
        document.append("_id", new BsonString(id)); 
        return findAsSingle(document); 
    }
    public Single<Order> save(final Order order)
    {
        return Single.fromPublisher(getCollection().insertOne(order))
        .map(success->order); 
    }
    
    

	public Single<Boolean> deleteById(String id) {
        BsonDocument document = new BsonDocument(); 
        document.append("_id", new BsonString(id)); 
        return Single.fromPublisher(getCollection().deleteOne(document))
        .map(success->Boolean.TRUE)
        .onErrorReturn(faile->Boolean.FALSE); 
        
	}

	public Single<Long> countBySessionNo(String id)
    {
        BsonDocument filter = new BsonDocument();

        filter.append("sessionNo", new BsonString(id));


        return Single.fromPublisher(getCollection().countDocuments(filter));
    }


    public Flowable<Order> getOrdersByUsernameAndRestaurant(String name, String restaurant) {
        BsonDocument filter =new BsonDocument()
                .append("restaurant", new BsonString(restaurant))
                .append("restaurantOwner", new BsonString(name));
        return
                findAsFlowable(filter);
    }
    public Flowable<Order> getOrdersByCityAndStatus(String name, String restaurant) {
        BsonDocument filter =new BsonDocument()
                .append("city", new BsonString(restaurant))
                .append("status", new BsonString(name));
        return findAsFlowable(filter);
    }
    public Flowable<Order> getOrdersBySessionAndStatus(String name, String restaurant) {
        BsonDocument filter =new BsonDocument()
                .append("sessionNo", new BsonString(restaurant))
                .append("status", new BsonString(name));
        return findAsFlowable(filter);
    }
    public Flowable<Order> getOrdersByRestaurantAndStatus(String name, String restaurant) {
        BsonDocument filter =new BsonDocument()
                .append("restaurant", new BsonString(restaurant))
                .append("status", new BsonString(name));
        return findAsFlowable(filter);
    }
    public Flowable<Order> getOrdersByRestaurantAndDate(String name, String restaurant) {
        BsonDocument filter =new BsonDocument()
                .append("restaurant", new BsonString(restaurant))
                .append("date", new BsonString(name));
        return findAsFlowable(filter);
    }



}