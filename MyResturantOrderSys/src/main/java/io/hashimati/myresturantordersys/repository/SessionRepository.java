package io.hashimati.myresturantordersys.repository;

import java.util.Date;

import javax.inject.Singleton;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;

import org.bson.BsonDocument;
import org.bson.BsonString;

import io.hashimati.myresturantordersys.domains.Session;
import io.hashimati.myresturantordersys.domains.SessionStatus;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * ResturantRepository
 */
@Singleton
public class SessionRepository {


	private final MongoClient mongoClient; 
	public SessionRepository(MongoClient mongoClient){

		this.mongoClient = mongoClient; 
	}
	
	private MongoCollection<Session> getCollection()
	{
		return mongoClient
				.getDatabase("restaurant")
				.getCollection("sessions", Session.class); 
	}
	private Single<Session> findAsSingle(BsonDocument query)
    {
        return Single
        .fromPublisher(getCollection()
        .find(query)); 
    }

    private Flowable<Session> findAsFlowable(BsonDocument query)
    {
        return Flowable
        .fromPublisher(getCollection()
        .find(query)); 
    }
    public Single<Session> findById(String id){
        BsonDocument document = new BsonDocument(); 
        document.append("_id", new BsonString(id)); 
        return findAsSingle(document); 
    }
    public Single<Long> countByOwner(String owner)
    {
        BsonDocument query = new BsonDocument(); 
        query.append("username", new BsonString(owner)); 
       return Single.fromPublisher(getCollection().countDocuments(query)); 
        
    }
    
    public Single<Long> countByRestaurantAndSession(String restaurant, SessionStatus status)
    {
        BsonDocument query = new BsonDocument(); 
        query.append("restaurant", new BsonString(restaurant))
        .append("status", new BsonString(status.toString())); 
       return Single.fromPublisher(getCollection().countDocuments(query)); 
        
    }
        
    public Single<Long> countByRestaurant(String restaurant)
    {
        BsonDocument query = new BsonDocument(); 
        query.append("restaurant", new BsonString(restaurant)); 
       return Single.fromPublisher(getCollection().countDocuments(query)); 
        
    }
    public Single<Session> save(Session session)
    {
        
        return Single.fromPublisher(getCollection().insertOne(session))
        .map(success->session); 
        
    }

	public Single<Boolean> deleteById(String id) {
        BsonDocument document = new BsonDocument(); 
        document.append("_id", new BsonString(id)); 
        return Single.fromPublisher(getCollection().deleteOne(document))
        .map(success->Boolean.TRUE)
        .onErrorReturn(faile->Boolean.FALSE); 
        
    }
    public Single<Session> getSessionByRestaurantAndStatus(String restaurant, SessionStatus sessionStatus){


        BsonDocument filter = new BsonDocument(); 
        filter.append("restaurant", new BsonString(restaurant))
        .append("status", new BsonString(sessionStatus.toString())); 

        return findAsSingle(filter); 

    }

    public Single<Session> changeSessionStatusByRestaurantAndStatus(String restaurant, SessionStatus sessionStatus){


        BsonDocument filter = new BsonDocument(); 
        filter.append("restaurant", new BsonString(restaurant))
        .append("status", new BsonString(SessionStatus.OPEN.toString())); 

       Session update = findAsSingle(filter).blockingGet(); 


       update.setStatus(sessionStatus);
        return Single.fromPublisher(getCollection().findOneAndReplace(filter, update))
        .onErrorReturn(error-> new Session(){
            {
                setStatus(SessionStatus.CLOSE);
            }
        }); 

    }

	public Single<String> closeSession(Session session) {

       BsonDocument query = new BsonDocument(); 
       query.append("_id", new BsonString(session.getId()));      
       
       session.setClosingTime(new Date());
       session.setStatus(SessionStatus.CLOSE);   
        return Single.fromPublisher(getCollection().findOneAndReplace(query, session))
        .map(success -> "Success")
        .onErrorReturn(error-> "Failed"); 
	}

	public Flowable<Session> getSessionByCityAndStatus(String city, SessionStatus status) {
        BsonDocument query = new BsonDocument(); 
       query.append("city", new BsonString(city))
       .append("status", new BsonString(status.toString())); 
    
        return findAsFlowable(query); 
	}
}