package com.monsite.gestioncahierdette.entity;


import lombok.Data;

@Data
public class Adresse {
    private String rue;
    private String ville;
    private String codePostal;
    private String pays;

    
    public Adresse(String rue, String ville, String codePostal, String pays) {
        this.rue = rue;
        this.ville = ville;
        this.codePostal = codePostal;
        this.pays = pays;
    }
}

