package io.hashimati.myresturantordersys.domains;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import io.hashimati.myresturantordersys.config.CalculationConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * @author Ahmed Al Hashmi @hashimati
 * Order
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    private String id; 
    private String username, resturantNo, branchNo, driverName = null,
    restaurantOwner;

    private java.time.LocalDate date = LocalDate.now();

    private long Number;
    //item and quantity
    private HashMap<MenuItem, Integer> items = new HashMap<MenuItem, Integer>();
    private String address;
    private OrderStatus status = OrderStatus.SENDING; 
    private PaymentType paymentType = PaymentType.CASH_ON_DELIVERY; 
    private double orderPrice; 
    private double deliveryFee; 
    private double VAT; 
    private double orderVAT; 
    private double deliveryFeeVAT;
    private double orderTotal;  
    private double total; 
    private String addressDescription; 
    private String sessionNo; 
    private CreationStatus creationStatus = CreationStatus.CREATED; 

    
    
    private ArrayList<String> restaurantLocation = new ArrayList<String>(),
            location = new ArrayList<String>();     
    private String notes;

    public double calculate(CalculationConfiguration config){
        total = 0.0; 
        orderTotal = 0.0; 
        for(MenuItem item : items.keySet()){
            orderTotal +=item.getPrice() * items.get(item);

            for(ItemOption io : item.getOptions())
                orderTotal +=io.getPrice(); 
        }
        this.orderVAT = orderTotal * config.getVatPercent(); 
        this.deliveryFee = config.getDeliveryFee(); 
        this.deliveryFeeVAT = deliveryFee * config.getVatPercent(); 
        return (total = orderTotal + orderVAT + deliveryFee + deliveryFeeVAT); 
    }


}