package io.hashimati.myresturantordersys.services;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jasypt.util.password.StrongPasswordEncryptor;

import io.hashimati.myresturantordersys.domains.security.User;
import io.hashimati.myresturantordersys.repository.UserRepository;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * UserService
 */

 @Singleton
public class UserService {

    @Inject
    private StrongPasswordEncryptor strongPasswordEncryptor ;

    @Inject
    private UserRepository userRepository; 
	public Single<String> save(User user) {
            user.setPassword(strongPasswordEncryptor.encryptPassword(user.getPassword())); 
            return userRepository.save(user);
    }

    public Single<Boolean> deleteById(String id) {
	    return userRepository.deleteById(id);
	}


    
}