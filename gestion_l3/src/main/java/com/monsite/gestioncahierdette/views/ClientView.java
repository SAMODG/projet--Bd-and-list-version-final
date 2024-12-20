package com.monsite.gestioncahierdette.views;

import com.monsite.gestioncahierdette.entity.Client;
import com.monsite.gestioncahierdette.services.ClientService;

import java.util.List;
import java.util.Scanner;

public class ClientView {

    private ClientService clientService;
    private Scanner scanner;

    public ClientView(ClientService clientService, Scanner scanner) {
        this.clientService = clientService;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        System.out.println("\n=== Gestion des Clients ===");
        System.out.println("1. Créer un client");
        System.out.println("2. Lister les clients avec ou sans compte utilisateur");
        System.out.println("3. Rechercher un client par téléphone");
        System.out.print("Choisissez une option : ");

        int choix = scanner.nextInt();
        scanner.nextLine();  // Consomme la nouvelle ligne

        switch (choix) {
            case 1:
                creerClient();
                break;
            case 2:
                listerClients();
                break;
            case 3:
                rechercherClientParTelephone();
                break;
            default:
                System.out.println("Option invalide.");
        }
    }

    private void creerClient() {
        System.out.print("Nom du client : ");
        String surname = scanner.nextLine();
        System.out.print("Téléphone : ");
        String telephone = scanner.nextLine();
        System.out.print("Adresse : ");
        String adresse = scanner.nextLine();
        System.out.print("Associer un compte utilisateur ? (oui/non) : ");
        String associerCompte = scanner.nextLine();

        if (associerCompte.equalsIgnoreCase("oui")) {
            System.out.print("Email : ");
            String email = scanner.nextLine();
            System.out.print("Login : ");
            String login = scanner.nextLine();
            System.out.print("Mot de passe : ");
            String password = scanner.nextLine();
            clientService.creerClient(surname, telephone, adresse, email, login, password);
        } else {
            clientService.creerClient(surname, telephone, adresse, null, null, null);
        }
        System.out.println("Client créé avec succès.");
    }

    private void listerClients() {
        System.out.print("Lister tous les clients ? (oui/non) : ");
        boolean tousLesClients = scanner.nextLine().equalsIgnoreCase("oui");
    
        List<Client> clients;
        if (tousLesClients) {
            clients = clientService.listerTousLesClients();  // Liste complète sans filtrage
        } else {
            System.out.print("Lister les clients avec compte utilisateur ? (oui/non) : ");
            boolean avecCompte = scanner.nextLine().equalsIgnoreCase("oui");
            clients = clientService.listerClients(avecCompte);  // Liste filtrée par comptes
        }
        
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé.");
        } else {
            System.out.println("Liste des clients :");
            clients.forEach(client -> 
                System.out.println("Nom : " + client.getSurname() + ", Téléphone : " + client.getTelephone()));
        }
    }
    

    private void rechercherClientParTelephone() {
        System.out.print("Téléphone du client : ");
        String telRecherche = scanner.nextLine();
        Client client = clientService.rechercherClientParTelephone(telRecherche);
        
        if (client != null) {
            System.out.println("Client trouvé :");
            System.out.println("Nom : " + client.getSurname());
            System.out.println("Téléphone : " + client.getTelephone());
            System.out.println("Adresse : " + client.getAdresseAsString());
        } else {
            System.out.println("Client non trouvé.");
        }
    }
}
