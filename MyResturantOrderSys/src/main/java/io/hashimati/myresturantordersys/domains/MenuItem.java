package io.hashimati.myresturantordersys.domains;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 * @author Ahmed Al Hashmi @hashimati
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {

    private String itemName, description; 
    private double price; 
    private ArrayList<ItemOption> options = new ArrayList<ItemOption>(); 
    private String category; 
}
