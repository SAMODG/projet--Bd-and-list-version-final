package com.monsite.gestioncahierdette.services;


import java.util.Date;
import java.util.List;

import com.monsite.gestioncahierdette.entity.Article;
import com.monsite.gestioncahierdette.entity.Client;
import com.monsite.gestioncahierdette.entity.Dette;

public interface DetteService {

    // Créer une nouvelle dette pour un client, avec articles et montant
    void creerDette(Client client, Date date, double montant, List<Article> articles);

    // Enregistrer un paiement pour une dette
    void enregistrerPaiement(Dette dette, Date date, double montant);

    // Lister les dettes non soldées d'un client
    List<Dette> listerDettesNonSoldees(Client client);
 
    // Lister les dettessoldées d'un client
    List<Dette> listerDettesSoldees();

    // Lister les dettes en cours avec la possibilité de filtrer par état (EnCours ou Annuler)
    List<Dette> listerDettesEnCours(String etat);


    //Archive les dettes 
    void archiverDettes(List<Dette> dettes);



     List<Dette> listerDettesNonSoldeesPourClient(Client client);

     boolean demanderDette(Client client, double montant);

     List<Dette> listerDemandesPourClient(Client client);

     boolean relancerDemande(Client client, int demandeId);

}

