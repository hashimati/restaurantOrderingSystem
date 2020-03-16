package io.hashimati.myresturantordersys.config;

import javax.inject.Singleton;
import io.micronaut.scheduling.annotation.Scheduled;

/**
 * @author Ahmed Al Hashmi @hashimati
 * SystemConfiguration
 */
@Singleton
public class SystemConfiguration {
    @Scheduled(fixedRate = "120s")
    public void garbageCollection()
    {
        System.gc(); 
    }
}