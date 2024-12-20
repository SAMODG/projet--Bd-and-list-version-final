package com.monsite.gestioncahierdette.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Client {
    private int id;
    private String surname;
    private String telephone;
    private Adresse adresse;
    private User user; 
  private List<Dette> dettes = new ArrayList<>();


    
    
    public Client() {}

    
    public Client(int id, String surname, String telephone, Adresse adresse) {
        this.id = id;
        this.surname = surname;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dettes = new ArrayList<>(); // Initialisation ici
    }


    public Client(String surname, String telephone, Adresse adresse) {
        this.surname = surname;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dettes = new ArrayList<>(); // Initialisation ici
    }

    public String getAdresseAsString() {
        return adresse.getRue() + ", " + adresse.getVille() + ", " + adresse.getCodePostal();
    }
}
