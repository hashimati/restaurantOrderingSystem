package io.hashimati.myresturantordersys.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.PropertySource;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Ahmed Al Hashmi @hashimati
 * CalculationConfiguration
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Introspected
@PropertySource(value = {
    
})
@ConfigurationProperties("calculation")
public class CalculationConfiguration {


    private double vatPercent; 
    private double deliveryFee; 
    private String currency; 

    
}