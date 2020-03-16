package io.hashimati.myresturantordersys.security;

import javax.inject.Singleton;
import org.jasypt.util.password.StrongPasswordEncryptor;
import io.micronaut.context.annotation.Factory;
/**
 * BCPasswordEncoder
 * @author Ahmed Al Hashmi @Hashimati
 *
 */
@Factory 
public class PasswordEncoder  {

    @Singleton
    public StrongPasswordEncryptor strongPasswordEncryptor(){
        return new StrongPasswordEncryptor(); 
    }
    
}