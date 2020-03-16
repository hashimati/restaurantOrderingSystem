package io.hashimati.myresturantordersys.services;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.hashimati.myresturantordersys.domains.Restaurant;
import io.hashimati.myresturantordersys.repository.RestaurantRepository;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * ResturantService
 */
 @Singleton
public class RestaurantService {
    @Inject
    private RestaurantRepository restaurantRepository; 
	public Single<Restaurant> save(Restaurant restaurant) {
		long count = restaurantRepository.countByOwner(restaurant.getUsername()).blockingGet().longValue();
		restaurant.setId(restaurant.getUsername() + "_"+ (++count));
		return restaurantRepository.save(restaurant);
	}
	public Single<Restaurant> getById(String id) {
		return restaurantRepository.findById(id);
	}
	public Single<Boolean> deleteById(String id) {
		return restaurantRepository.deleteById(id);
	}


    
}