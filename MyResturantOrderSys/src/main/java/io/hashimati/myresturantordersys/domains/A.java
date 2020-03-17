package io.hashimati.myresturantordersys.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * A
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class A {
    private int x; 
    private LocalDate localDate = LocalDate.now();
    private LocalDateTime localDateTime = LocalDateTime.now();
    private Date date = new Date();



}