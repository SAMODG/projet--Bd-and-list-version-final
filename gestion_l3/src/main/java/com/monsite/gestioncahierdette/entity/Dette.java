package com.monsite.gestioncahierdette.entity;


import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class Dette {
    private int id;  
    private Date date;
    private double montant; 
    private double montantVerse;
    private double montantRestant;
    private List<Article> articles;
    private List<Paiement> paiements;
    private Client client;  

     private boolean archive; 
    private String etat;    
    private boolean estSoldee;
    
    
    public Dette() {}

    // Constructeur
    public Dette(Client client, Date date, double montant, List<Article> articles) {
        this.client = client;
        this.date = date;
        this.montant = montant;
        this.montantRestant = montant;
        this.articles = articles;
        this.archive = false; // Par défaut, une dette n'est pas archivée
        this.archive = false; // Par défaut, une dette n'est pas archivée
        this.etat = "EN COURS"; // Par défaut, l'état est "EN COURS"
        this.estSoldee = false; // Par défaut, une dette n'est pas soldée
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   
    public double getMontantTotal() {
        return montant;
    }

    public void setMontantTotal(double montant) {
        this.montant = montant;
    }

    
    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public boolean isEstSoldee() {
        return estSoldee;
    }

    public void setEstSoldee(boolean estSoldee) {
        this.estSoldee = estSoldee;
    }
}
