package com.monsite.gestioncahierdette.services;



import java.util.List;

import com.monsite.gestioncahierdette.entity.Client;

public interface ClientService {

    // Créer un client et associer éventuellement un compte utilisateur
    void creerClient(String surname, String telephone, String adresse, String email, String login, String password);

    // Lister tous les clients avec possibilité de filtrer ceux qui ont des comptes
    List<Client> listerClients(boolean avecCompte);

    // Nouvelle méthode pour lister tous les clients sans filtrage
    List<Client> listerTousLesClients();

    // Rechercher un client par téléphone
    Client rechercherClientParTelephone(String telephone);

    // Calculer le cumul des montants dus pour chaque client
    double calculerMontantsDus(Client client);


    
    Client getCurrentClient();
    void setCurrentClient(Client client);

}

