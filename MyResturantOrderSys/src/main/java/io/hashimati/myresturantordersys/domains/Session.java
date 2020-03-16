package io.hashimati.myresturantordersys.domains;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ahmed Al Hashmi @hashimati
 * Session
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    private String id; 
    private String restaurant; 
    private String username; 
    private Date openingTime; 
    private Date closingTime; 
    private SessionStatus status = SessionStatus.OPEN; 
    private CreationStatus creationStatus = CreationStatus.CREATED; 
    private String city; 
    

}