package io.hashimati.myresturantordersys.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ahmed Al Hashmi @hashimati
 * Resturant
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {


    private String id; 
    private String username; 
 
    private String name, city, country, address; 

    private CreationStatus creationStatus = CreationStatus.CREATED; 

    
}