package com.monsite.gestioncahierdette.entity;

import lombok.Data;

@Data
public class Article {
    private int id;
    private String nom;
    private int qteStock;

    
     public Article() {}


   
    public Article(int id, String nom, int qteStock) {
        this.id = id;
        this.nom = nom;
        this.qteStock = qteStock;
    }

    
    public Article(String nom, int qteStock) {
        this.nom = nom;
        this.qteStock = qteStock;
    }
}
