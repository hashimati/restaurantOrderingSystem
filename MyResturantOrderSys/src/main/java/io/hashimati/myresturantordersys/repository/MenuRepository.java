package io.hashimati.myresturantordersys.repository;

import javax.inject.Singleton;

import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.Success;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonString;

import io.hashimati.myresturantordersys.domains.Menu;
import io.reactivex.Flowable;
import io.reactivex.Single;

/** 
 * @author Ahmed Al Hashmi @hashimati
 * MenuRepository
 */
@Singleton
public class MenuRepository {


    private final MongoClient mongoClient; 
	public MenuRepository(MongoClient mongoClient){

		this.mongoClient = mongoClient; 
	}
	
	private MongoCollection<Menu> getCollection()
	{
		return mongoClient
				.getDatabase("restaurant")
				.getCollection("menus", Menu.class); 
	}
	private Single<Menu> findAsSingle(BsonDocument query)
    {
        return Single
        .fromPublisher(getCollection()
        .find(query)); 
    }

    private Flowable<Menu> findAsFlowable(BsonDocument query)
    {
        return Flowable
        .fromPublisher(getCollection()
        .find(query)); 
    }
    public Single<Menu> findById(String id){
        BsonDocument document = new BsonDocument(); 
        document.append("_id", new BsonString(id)); 
        return findAsSingle(document); 
    }
    public Single<Menu> save(Menu menu)
    {
        InsertOneOptions options = new InsertOneOptions(); 
        options.bypassDocumentValidation(true);
        return Single.fromPublisher(getCollection().insertOne(menu, options))
        .map(success-> menu); 
    }
    public Single<String> update(Menu menu)
    {
        
        BsonDocument filter = new BsonDocument(); 
        filter.append("_id", new BsonString(menu.getId())); 
        return Single.fromPublisher(getCollection().findOneAndReplace(filter,menu))
        .map(success-> "Success")
        .onErrorReturnItem("failed"); 
    }
    
	public Single<Boolean> deleteById(String id) {
        BsonDocument document = new BsonDocument(); 
        document.append("_id", new BsonString(id)); 
        return Single.fromPublisher(getCollection().deleteOne(document))
        .map(success->Boolean.TRUE)
        .onErrorReturn(faile->Boolean.FALSE); 
        
	}
}