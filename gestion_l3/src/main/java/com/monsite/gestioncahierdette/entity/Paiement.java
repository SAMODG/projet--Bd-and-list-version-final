package com.monsite.gestioncahierdette.entity;


import lombok.Data;
import java.util.Date;

@Data
public class Paiement {
    private int id; 
    private Date date;
    private double montant;
    private Dette dette; 


    public Paiement() {}

   
    public Paiement(Date date, double montant, Dette dette) {
        this.date = date;
        this.montant = montant;
        this.dette = dette;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
