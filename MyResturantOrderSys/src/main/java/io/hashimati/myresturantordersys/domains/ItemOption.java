package io.hashimati.myresturantordersys.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author Ahmed Al Hashmi @hashimati
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOption {

    private String name; 
    private double price; 
    private String description; 
    private String category; 
     
}
