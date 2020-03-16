package io.hashimati.myresturantordersys.repository;

import javax.inject.Singleton;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;

import org.bson.BsonDocument;
import org.bson.BsonString;

import io.hashimati.myresturantordersys.domains.security.User;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * UserRepository
 */


@Singleton
public class UserRepository {

    private final MongoClient mongoClient; 
	public UserRepository(MongoClient mongoClient){

		this.mongoClient = mongoClient; 
	}
	
	private MongoCollection<User> getCollection()
	{
		return mongoClient
				.getDatabase("restaurant")
				.getCollection("users", User.class); 
	}
	private Single<User> findAsSingle(BsonDocument query)
    {
        return Single
        .fromPublisher(getCollection()
        .find(query)); 
    }

    private Flowable<User> findAsFlowable(BsonDocument query)
    {
        return Flowable
        .fromPublisher(getCollection()
        .find(query)); 
    }
    public Single<User> findById(String id){
        BsonDocument document = new BsonDocument(); 
        document.append("_id", new BsonString(id)); 
        return findAsSingle(document); 
    }
    public Single<String> save(User user)
    {
        return Single.fromPublisher(getCollection().insertOne(user))
        .map(success->"Success")
        .onErrorReturn(error->"Failed");  
    }
    
	public Single<Boolean> deleteById(String id) {
        BsonDocument document = new BsonDocument(); 
        document.append("_id", new BsonString(id)); 
        return Single.fromPublisher(getCollection().deleteOne(document))
        .map(success->Boolean.TRUE)
        .onErrorReturn(faile->Boolean.FALSE); 
        
	}

}