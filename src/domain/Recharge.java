package domain;

import java.time.LocalDate;

public class Recharge{
	
	 private String carnet;
     private double amount;
     private LocalDate rechargeDate;
     private String name;
     
     public Recharge() {
    	 
     }

     // Constructor
     public Recharge(String carnet, double amount, LocalDate rechargeDate ) {
         this.carnet = carnet;
         this.amount = amount;
         this.rechargeDate = rechargeDate;
     }

     // Getters y setters
     public String getCarnet() {
         return carnet;
     }

     public void setCarnet(String carnet) {
         this.carnet = carnet;
     }

     public double getAmount() {
         return amount;
     }

     public void setAmount(double amount) {
         this.amount = amount;
     }

     public LocalDate getRechargeDate() {
         return rechargeDate;
     }
     
     

     public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRechargeDate(LocalDate rechargeDate) {
         this.rechargeDate = rechargeDate;
     }

   
 
}
