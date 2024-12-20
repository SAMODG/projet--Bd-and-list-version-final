package com.monsite.gestioncahierdette.services.impl;

import com.monsite.gestioncahierdette.entity.Adresse;

import com.monsite.gestioncahierdette.entity.Client;
import com.monsite.gestioncahierdette.entity.User;
import com.monsite.gestioncahierdette.entity.User.Role;
import com.monsite.gestioncahierdette.repositories.ripo.ClientRepository;
import com.monsite.gestioncahierdette.repositories.ripo.UserRepository;
import com.monsite.gestioncahierdette.services.ClientService;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private Client currentClient;


    public ClientServiceImpl(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void creerClient(String surname, String telephone, String adresse, String email, String login, String password) {
        User user = null;
        if (email != null && login != null && password != null) {
            user = new User(email, login, password, Role.CLIENT); // Définit le rôle par défaut si nécessaire
            userRepository.ajouter(user); // Ajoute l'utilisateur dans la base de données, en assignant l'ID
        }
    
        // Crée l'adresse du client
        Adresse adresseObj = new Adresse(adresse, "", "", ""); // Adresse simplifiée
        Client client = new Client(surname, telephone, adresseObj);
        client.setUser(user); // Associe l'utilisateur au client
    
        clientRepository.ajouter(client); // Ajoute le client avec user_id ou null
    }


    
    
    @Override
    public List<Client> listerClients(boolean avecCompte) {
        return clientRepository.listerAvecCompte(avecCompte); // Utilisation du repository pour filtrer
    }

            @Override
        public List<Client> listerTousLesClients() {
            return clientRepository.listerTous(); // Appel à la méthode listerTous() du repository
        }

    @Override
    public Client rechercherClientParTelephone(String telephone) {
        return clientRepository.trouverParTelephone(telephone); // Utilisation du repository pour la recherche
    }

    @Override
    public double calculerMontantsDus(Client client) {
        return client.getDettes().stream()
                .mapToDouble(dette -> dette.getMontantRestant())
                .sum();
    }


    

    @Override
    public Client getCurrentClient() {
        return currentClient;
    }

        @Override
    public void setCurrentClient(Client client) {
        this.currentClient = client;
        System.out.println("Client actuel défini : " + client.getSurname());
    }


         



}
