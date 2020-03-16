package io.hashimati.myresturantordersys.domains;

import java.util.HashSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ahmed Al Hashmi @hashimati
 * IMenu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class IMenu {

    private String category; 

    private HashSet<MenuItem> items = new HashSet<MenuItem>(); 
}