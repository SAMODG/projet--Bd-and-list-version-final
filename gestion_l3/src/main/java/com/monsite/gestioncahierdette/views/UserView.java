package com.monsite.gestioncahierdette.views;

import com.monsite.gestioncahierdette.entity.Client;
import com.monsite.gestioncahierdette.entity.User;
import com.monsite.gestioncahierdette.entity.User.Role;
import com.monsite.gestioncahierdette.services.ClientService;
import com.monsite.gestioncahierdette.services.UserService;

import java.util.List;
import java.util.Scanner;

public class UserView {

    private final ClientService clientService; 

    private UserService userService;
    private Scanner scanner;

    public UserView(UserService userService,ClientService clientService, Scanner scanner) {
        this.clientService = clientService;
        this.userService = userService;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        System.out.println("\n=== Gestion des Utilisateurs ===");
        System.out.println("1. Créer un utilisateur");
        System.out.println("2. Activer/Désactiver un utilisateur");
        System.out.println("3. Lister les utilisateurs");
        System.out.print("Choisissez une option : ");

        int choix = scanner.nextInt();
        scanner.nextLine();  // Consomme la nouvelle ligne

        switch (choix) {
            case 1:
                creerUtilisateur();
                break;
            case 2:
                activerDesactiverUtilisateur();
                break;
            case 3:
                listerUtilisateurs();
                break;
            default:
                System.out.println("Option invalide.");
        }
    }

    private void creerUtilisateur() {
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Login : ");
        String login = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();
        System.out.print("Rôle (BOUTIQUIER, ADMIN, CLIENT) : ");
        Role role = Role.valueOf(scanner.nextLine().toUpperCase());
        userService.creerCompteUtilisateurPourClient(email, login, password, role);
        System.out.println("Utilisateur créé avec succès.");
    }


    private void listerUtilisateurs() {
        System.out.print("Lister les utilisateurs actifs ? (oui/non) : ");
        boolean actifs = scanner.nextLine().equalsIgnoreCase("oui");
        System.out.print("Rôle (BOUTIQUIER, ADMIN, CLIENT ou laisser vide pour tous) : ");
        String roleInput = scanner.nextLine().toUpperCase();
        Role role = roleInput.isEmpty() ? null : Role.valueOf(roleInput);

        List<User> utilisateurs = userService.listerComptes(actifs, role);
        
        if (utilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé avec ces critères.");
        } else {
            System.out.println("Liste des utilisateurs :");
            utilisateurs.forEach(user -> System.out.println("Login : " + user.getLogin() + ", Rôle : " + user.getRole() + ", Actif : " + user.isActive()));
        }
    }

   
    
        // Créer un compte utilisateur pour un client sans compte
        public void creerComptePourClientSansCompte() {
            System.out.println("\n=== Créer un compte utilisateur pour un client ===");
            System.out.print("Téléphone du client : ");
            String telephone = scanner.nextLine();
    
            // Recherche du client sans compte
            Client client = clientService.rechercherClientParTelephone(telephone);


            if (client != null && client.getUser() == null) {
                System.out.print("Email : ");
                String email = scanner.nextLine();
                System.out.print("Login : ");
                String login = scanner.nextLine();
                System.out.print("Mot de passe : ");
                String password = scanner.nextLine();
    
                userService.creerCompteUtilisateurPourClient(email, login, password, Role.CLIENT);
                System.out.println("Compte utilisateur créé pour le client : " + client.getSurname());
            } else {
                System.out.println("Aucun client trouvé ou ce client a déjà un compte utilisateur.");
            }
        }
    
        // Créer un compte utilisateur avec un rôle spécifique
        public void creerCompteUtilisateurAvecRole() {
            System.out.println("\n=== Créer un compte utilisateur avec rôle ===");
            System.out.print("Email : ");
            String email = scanner.nextLine();
            System.out.print("Login : ");
            String login = scanner.nextLine();
            System.out.print("Mot de passe : ");
            String password = scanner.nextLine();
            System.out.print("Rôle (BOUTIQUIER/ADMIN) : ");
            Role role = Role.valueOf(scanner.nextLine().toUpperCase());
    
            userService.creerCompteUtilisateurAvecRole(email, login, password, role);
            System.out.println("Compte utilisateur créé avec succès.");
        }
    
        // Désactiver ou activer un compte utilisateur
        public void activerDesactiverUtilisateur() {
            System.out.println("\n=== Activer/Désactiver un utilisateur ===");
            System.out.print("ID de l'utilisateur : ");
            int userId = scanner.nextInt();
            scanner.nextLine(); // Consomme la nouvelle ligne
        
            // Récupération de l'utilisateur
            User user = userService.trouverParId(userId);
            if (user == null) {
                System.out.println("Utilisateur non trouvé avec l'ID : " + userId);
                return;
            }
        
            System.out.print("Voulez-vous activer (1) ou désactiver (2) cet utilisateur ? : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la nouvelle ligne
        
            try {
                switch (choix) {
                    case 1:
                        userService.activerCompte(user);
                        break;
                    case 2:
                        userService.desactiverCompte(user);
                        break;
                    default:
                        System.out.println("Option invalide. Aucun changement effectué.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
        
    
        // Lister les comptes utilisateurs par rôle ou actifs
        public void listerComptesParRoleOuActifs() {
            System.out.println("\n=== Lister les comptes utilisateurs ===");
            System.out.print("Actifs uniquement (oui/non) : ");
            boolean actifs = scanner.nextLine().equalsIgnoreCase("oui");
            System.out.print("Filtrer par rôle (ADMIN/BOUTIQUIER/TOUT) : ");
            String roleInput = scanner.nextLine();
            Role role = "TOUT".equalsIgnoreCase(roleInput) ? null : Role.valueOf(roleInput.toUpperCase());
    
            List<User> utilisateurs = userService.listerComptes(actifs, role);
            if (utilisateurs.isEmpty()) {
                System.out.println("Aucun utilisateur trouvé.");
            } else {
                utilisateurs.forEach(user -> 
                    System.out.println("Login : " + user.getLogin() + ", Rôle : " + user.getRole() + ", Actif : " + user.isActive()));
            }
        }
    
    
}
