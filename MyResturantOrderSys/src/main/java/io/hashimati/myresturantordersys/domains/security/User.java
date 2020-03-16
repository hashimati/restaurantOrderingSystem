package io.hashimati.myresturantordersys.domains.security;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

import io.hashimati.myresturantordersys.domains.CreationStatus; 


/**
 * @author Ahmed Al Hashmi @hashimati
 * User
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id; 
    private String mobilenumber; 
    private String email; 
    private String password; 
    private boolean activated;
    private Date registrationDate;
    private ArrayList<String> roles = new ArrayList<String>(); 
    private CreationStatus creationStatus = CreationStatus.CREATED; 
}