package io.hashimati.myresturantordersys.domains;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ahmed Al Hashmi @hashimati
 * Menu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends IMenu{
    private String id; 
    private String username; 
    private String restaurant; 
    private ArrayList<IMenu> subMenus= new ArrayList<IMenu>(); 
    private CreationStatus creationStatus = CreationStatus.CREATED; 
}