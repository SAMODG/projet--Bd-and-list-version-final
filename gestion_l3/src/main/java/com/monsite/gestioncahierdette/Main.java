package com.monsite.gestioncahierdette;

import com.monsite.gestioncahierdette.config.Configuration;
import com.monsite.gestioncahierdette.core.bd.DatabaseImpl;
import com.monsite.gestioncahierdette.entity.Client;
import com.monsite.gestioncahierdette.factories.RepositoryFactory;
import com.monsite.gestioncahierdette.factories.RepositoryFactoryChooser;
import com.monsite.gestioncahierdette.repositories.ripo.*;
import com.monsite.gestioncahierdette.services.impl.*;
import com.monsite.gestioncahierdette.views.*;

import java.util.Scanner;

public class Main {

    
    
    
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
    
            
            choisirModeStockage(scanner);
    
            // Factory pour basculer entre liste et base de données
        RepositoryFactory factory = RepositoryFactoryChooser.getFactory();

          
            ClientRepository clientRepository = factory.getClientRepository();
            UserRepository userRepository = factory.getUserRepository();

            // Initialisation de clientService avant choisirRole
        ClientServiceImpl clientService = new ClientServiceImpl(clientRepository, userRepository);

       
        String role = choisirRole(scanner, clientService);

        
        // Test de la connexion si base de données utilisée
        if (factory instanceof com.monsite.gestioncahierdette.factories.BdRepositoryFactory) {
            DatabaseImpl db = new DatabaseImpl();
            if (!db.testerConnexion()) {
                System.out.println("Impossible de se connecter à la base de données. Vérifiez les paramètres de connexion.");
                return; // Arrête l'application si la connexion échoue
            }
        }

       
        DetteRepository detteRepository = factory.getDetteRepository();
        PaiementRepository paiementRepository = factory.getPaiementRepository();
        ArticleRepository articleRepository = factory.getArticleRepository();
       
       
       
        DetteServiceImpl detteService = new DetteServiceImpl(detteRepository, paiementRepository);
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        ArticleServiceImpl articleService = new ArticleServiceImpl(articleRepository);
        
     


        
        ClientView clientView = new ClientView(clientService, scanner);
        DetteView detteView = new DetteView(detteService, clientService, articleService, scanner);
        UserView userView = new UserView(userService,clientService, scanner);
        ArticleView articleView = new ArticleView(articleService, scanner);

        
        afficherMenuPrincipal(role, clientView, detteView, userView, articleView, scanner);

        scanner.close();
        System.out.println("Application terminée.");
    }

   
    private static void choisirModeStockage(Scanner scanner) {
        boolean valide = false;
        while (!valide) {
            System.out.println("\n=== Choix du Mode de Stockage ===");
            System.out.println("1. Stockage en base de données");
            System.out.println("2. Stockage en liste (mémoire)");
            System.out.println("3. Quitter l'application");
            System.out.print("Choisissez une option (1/2/3) : ");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la nouvelle ligne

            switch (choix) {
                case 1:
                    Configuration.setModeStockage(Configuration.ModeStockage.BASE_DE_DONNEES);
                    System.out.println("Mode de stockage : BASE DE DONNÉES");
                    valide = true;
                    break;
                case 2:
                    Configuration.setModeStockage(Configuration.ModeStockage.LISTE);
                    System.out.println("Mode de stockage : LISTE (en mémoire)");
                    valide = true;
                    break;
                case 3:
                    System.out.println("Vous avez quitté l'application.");
                    System.exit(0); // Quitte l'application
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    // Méthode pour choisir le rôle utilisateur (ajout de l'option retour)
    private static String choisirRole(Scanner scanner, ClientServiceImpl clientService) {
        while (true) {
            System.out.println("\n=== Choix du Rôle ===");
            System.out.println("1. Boutiquier");
            System.out.println("2. Admin");
            System.out.println("3. Client");
            System.out.println("4. Quitter l'application");
            System.out.print("Choisissez un rôle (1/2/3/4) : ");
    
            int choix = scanner.nextInt();
            scanner.nextLine();
    
            switch (choix) {
                case 1:
                    System.out.println("Rôle sélectionné : Boutiquier");
                    return "Boutiquier";
                case 2:
                    System.out.println("Rôle sélectionné : Admin");
                    return "Admin";
                case 3:
                    System.out.println("Rôle sélectionné : Client");
                    System.out.print("Veuillez entrer votre téléphone : ");
                    String telClient = scanner.nextLine();
                    Client client = clientService.rechercherClientParTelephone(telClient);
    
                    if (client != null) {
                        clientService.setCurrentClient(client); // Définir le client actuel
                        return "Client";
                    } else {
                        System.out.println("Client non trouvé. Retour au menu principal.");
                        break;
                    }
                case 4:
                    System.out.println("Vous avez quitté l'application.");
                    System.exit(0); // Quitte l'application
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }
    
    



    
       
    private static void afficherMenuPrincipal(String role, ClientView clientView, DetteView detteView, UserView userView, ArticleView articleView, Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Gestion du Cahier de Dette (" + role + ") ===");

            if ("Admin".equalsIgnoreCase(role)) {
                // Menu spécifique pour le rôle Admin
                System.out.println("1. Créer un compte utilisateur pour un client sans compte");
                System.out.println("2. Créer un compte utilisateur avec un rôle spécifique");
                System.out.println("3. Désactiver/Activer un compte utilisateur");
                System.out.println("4. Lister les comptes utilisateurs actifs ou par rôle");
                System.out.println("5. Créer un article");
                System.out.println("6. Lister les articles disponibles");
                System.out.println("7. Mettre à jour la quantité en stock d’un article");
                System.out.println("8. Archiver les dettes soldées");
                System.out.println("9. Retour");
            } else if ("Boutiquier".equalsIgnoreCase(role)) {
                // Menu spécifique pour le rôle Boutiquier
                System.out.println("1. Gestion des clients");
                System.out.println("2. Gestion des dettes");
                System.out.println("3. Retour");
            } else if ("Client".equalsIgnoreCase(role)) {
                // Menu spécifique pour le rôle Client
                System.out.println("1. Lister mes dettes non soldées");
                System.out.println("2. Faire une demande de dette");
                System.out.println("3. Lister mes demandes de dette");
                System.out.println("4. Envoyer une relance pour une demande de dette annulée");
                System.out.println("5. Retour");
            } else {
                System.out.println("Rôle inconnu. Retour au menu principal.");
                break;
            }

            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (role.toLowerCase()) {
                case "admin":
                    switch (choix) {
                        case 1:
                            userView.creerComptePourClientSansCompte(); 
                            break;
                        case 2:
                            userView.creerCompteUtilisateurAvecRole(); 
                            break;
                        case 3:
                            userView.activerDesactiverUtilisateur(); 
                            break;
                        case 4:
                            userView.listerComptesParRoleOuActifs(); 
                            break;
                        case 5:
                            articleView.creerArticle();
                            break;
                        case 6:
                            articleView.listerArticlesDisponibles();
                            break;
                        case 7:
                            articleView.mettreAJourStock();
                            break;
                        case 8:
                            detteView.archiverDettesSoldees(); 
                            break;
                        case 9:
                            running = false;
                            break;
                        default:
                            System.out.println("Option invalide. Veuillez réessayer.");
                    }
                    break;

                case "boutiquier":
                    switch (choix) {
                        case 1:
                            clientView.afficherMenu();
                            break;
                        case 2:
                            detteView.afficherMenu();
                            break;
                        case 3:
                            running = false;
                            break;
                        default:
                            System.out.println("Option invalide. Veuillez réessayer.");
                    }
                    break;

                case "client":
                    switch (choix) {
                        case 1:
                            detteView.listerDettesNonSoldeesPourClient(); 
                            break;
                        case 2:
                            detteView.demanderDette(); 
                            break;
                        case 3:
                            detteView.listerDemandesDettePourClient(); 
                            break;
                        case 4:
                            detteView.relancerDemandeDette(); 
                            break;
                        case 5:
                            running = false;
                            break;
                        default:
                            System.out.println("Option invalide. Veuillez réessayer.");
                    }
                    break;

                default:
                    System.out.println("Option invalide. Retour au menu principal.");
                    running = false;
            }
        }
    }

 }
