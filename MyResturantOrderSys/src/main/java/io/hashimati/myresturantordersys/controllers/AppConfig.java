package io.hashimati.myresturantordersys.controllers;

import javax.inject.Inject;

import io.hashimati.myresturantordersys.config.CalculationConfiguration;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

/**
 * @author Ahmed Al Hashmi @hashimati
 * ResourceConfig
 */
@Controller("/v1/api/app")
public class AppConfig {

    @Inject
    private CalculationConfiguration calculationConfiguration; 
    
    @Get("/calculation")
    public CalculationConfiguration calculationConfiguration()
    {

        return calculationConfiguration; 
    }
}