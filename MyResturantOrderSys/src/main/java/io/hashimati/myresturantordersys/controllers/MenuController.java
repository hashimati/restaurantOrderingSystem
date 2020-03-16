package io.hashimati.myresturantordersys.controllers;

import java.io.IOException;

import javax.inject.Inject;

import io.hashimati.myresturantordersys.domains.CreationStatus;
import io.hashimati.myresturantordersys.domains.Menu;
import io.hashimati.myresturantordersys.domains.MenuItem;
import io.hashimati.myresturantordersys.services.MenuService;
import io.hashimati.myresturantordersys.services.OrderProducer;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.security.authentication.Authentication;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * MenuController
 */
@Controller("/api/menu")
public class MenuController {

    
    @Inject
    private MenuService menuService; 
  
    @Post("/save")
    public Single<Menu> save( Menu menu, Authentication authentication){

        if(!authentication.getName().equals(menu.getUsername()))
        {
            return Single.just(new Menu(){{
                setUsername("Invalid!");
            }});

        } 
        return menuService.save(menu); 
        
    }

    
    @Post(value = "/uploadMenu/{username}/{restaurant}", consumes = MediaType.MULTIPART_FORM_DATA)
    public Single<Menu> uploadMenu(CompletedFileUpload file,@PathVariable("username") String username, @PathVariable("restaurant")String restaurant,  Authentication authentication)
    {
        if(!authentication.getName().equals(username))
        {
            return Single.just(new Menu(){{
                setCreationStatus(CreationStatus.FAILED);
            }}); 
        }

        try {
			return menuService.createMenu(username, restaurant, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
            e.printStackTrace();
            return Single.just(new Menu(){{
                setCreationStatus(CreationStatus.FAILED);
            }});  
		}         
    }
    @Post("/{username}/{menuId}/addItem")
    public Single<String> addItem(@Body MenuItem menuItem, @PathVariable("username") String userName,@PathVariable("menuId")String menuId,  Authentication authentication ){
     
        if(!authentication.getName().equals(userName))
        {
            return Single.just("Failed"); 

        } 
        System.out.println(menuItem); 
        return menuService.addItem(menuItem, menuId); 
        
    }

    // @Post("/{menu}/addItem")
    // public boolean addItem(@Body MenuItem menuItem,@PathVariable("menu") String id )
    // {
    //     return menuService.addItem(menuItem, id); 
    // }
    @Inject
    private OrderProducer  orderProducer ;
    
  

}