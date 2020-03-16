package io.hashimati.myresturantordersys.security;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.reactivestreams.Publisher;

import io.hashimati.myresturantordersys.domains.security.User;
import io.hashimati.myresturantordersys.repository.UserRepository;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.Flowable;

/**
 * 
 * @author Ahmed Al Hashmi @Hashimati
 *
 */

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider  {

    @Inject
    private UserRepository userRepository;

    // @Inject 
   
    @Inject
    private StrongPasswordEncryptor strongPasswordEncryptor ;


    @Override
    public Publisher<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {

        System.out.println(authenticationRequest.getIdentity()); 
        //if User is not exist return Authentication Failed
        User user = userRepository.findById(authenticationRequest.getIdentity().toString()).blockingGet();
        
        if(user == null){
            
            return Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.USER_NOT_FOUND)); 
        }
        // else if(!user.isActivated()){
        //     return Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.USER_DISABLED)); 
        // }

        if ( strongPasswordEncryptor.checkPassword(authenticationRequest.getSecret().toString(), user.getPassword())) {
           System.out.println("It is authenticated!"); 
            return Flowable.just(new UserDetails(user.getId(), user.getRoles()));
        }

        return Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)); 
    }
}