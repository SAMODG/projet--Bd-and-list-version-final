package com.monsite.gestioncahierdette.views;

import com.monsite.gestioncahierdette.entity.Article;
import com.monsite.gestioncahierdette.entity.Client;
import com.monsite.gestioncahierdette.entity.Dette;
import com.monsite.gestioncahierdette.services.ClientService;
import com.monsite.gestioncahierdette.services.DetteService;
import com.monsite.gestioncahierdette.services.ArticleService;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DetteView {

    private DetteService detteService;
    private ClientService clientService;
    private ArticleService articleService;
    private Scanner scanner;

    public DetteView(DetteService detteService, ClientService clientService, ArticleService articleService, Scanner scanner) {
        this.detteService = detteService;
        this.clientService = clientService;
        this.articleService = articleService;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        System.out.println("\n=== Gestion des Dettes ===");
        System.out.println("1. Créer une dette pour un client");
        System.out.println("2. Enregistrer un paiement pour une dette");
        System.out.println("3. Lister les dettes non soldées d'un client");
        System.out.print("Choisissez une option : ");

        int choix = scanner.nextInt();
        scanner.nextLine();  // Consomme la nouvelle ligne

        switch (choix) {
            case 1:
                creerDette();
                break;
            case 2:
                enregistrerPaiement();
                break;
            case 3:
                listerDettesNonSoldees();
                break;
            default:
                System.out.println("Option invalide.");
        }
    }

    private void creerDette() {
        System.out.print("Téléphone du client : ");
        String telClient = scanner.nextLine();
        Client client = clientService.rechercherClientParTelephone(telClient);
        
        if (client != null) {
            System.out.print("Montant de la dette : ");
            double montant = scanner.nextDouble();
            scanner.nextLine();
            
            List<Article> articles = articleService.listerArticlesDisponibles();
            if (articles.isEmpty()) {
                System.out.println("Aucun article disponible pour créer une dette.");
                return;
            }

            detteService.creerDette(client, new Date(), montant, articles);
            System.out.println("Dette créée avec succès pour " + client.getSurname());
        } else {
            System.out.println("Client non trouvé.");
        }
    }

    private void enregistrerPaiement() {
        System.out.println("Enregistrement du paiement pour une dette.");
        
        System.out.print("Téléphone du client : ");
        String telClient = scanner.nextLine();
        Client client = clientService.rechercherClientParTelephone(telClient);
        
        if (client != null) {
            List<Dette> dettesNonSoldees = detteService.listerDettesNonSoldees(client);
            
            if (dettesNonSoldees.isEmpty()) {
                System.out.println("Aucune dette non soldée pour ce client.");
                return;
            }
            
            System.out.println("Sélectionnez une dette à payer :");
            for (int i = 0; i < dettesNonSoldees.size(); i++) {
                Dette dette = dettesNonSoldees.get(i);
                System.out.println((i + 1) + ". Montant restant : " + dette.getMontantRestant());
            }
            
            int index = scanner.nextInt() - 1;
            scanner.nextLine();
            if (index >= 0 && index < dettesNonSoldees.size()) {
                Dette dette = dettesNonSoldees.get(index);
                System.out.print("Montant du paiement : ");
                double montant = scanner.nextDouble();
                scanner.nextLine();

                detteService.enregistrerPaiement(dette, new Date(), montant);
                System.out.println("Paiement enregistré avec succès.");
            } else {
                System.out.println("Choix invalide.");
            }
        } else {
            System.out.println("Client non trouvé.");
        }
    }

    private void listerDettesNonSoldees() {
        System.out.print("Téléphone du client : ");
        String telClient = scanner.nextLine();
        Client client = clientService.rechercherClientParTelephone(telClient);
        
        if (client != null) {
            List<Dette> dettesNonSoldees = detteService.listerDettesNonSoldees(client);
            
            if (dettesNonSoldees.isEmpty()) {
                System.out.println("Aucune dette non soldée pour ce client.");
            } else {
                System.out.println("Dettes non soldées pour le client " + client.getSurname() + ":");
                dettesNonSoldees.forEach(dette -> 
                    System.out.println("Dette de " + dette.getMontantRestant() + " restant."));
            }
        } else {
            System.out.println("Client non trouvé.");
        }
    }

    // Archiver les dettes soldées
    public void archiverDettesSoldees() {
        System.out.println("\n=== Archiver les dettes soldées ===");
        List<Dette> dettesSoldees = detteService.listerDettesSoldees();
        if (dettesSoldees.isEmpty()) {
            System.out.println("Aucune dette soldée à archiver.");
        } else {
            detteService.archiverDettes(dettesSoldees);
            System.out.println("Dettes soldées archivées avec succès.");
        }
    }


    // Lister les dettes non soldées pour le client actuel
public void listerDettesNonSoldeesPourClient() {
    System.out.println("\n=== Mes dettes non soldées ===");
    List<Dette> dettes = detteService.listerDettesNonSoldeesPourClient(clientService.getCurrentClient());
    if (dettes.isEmpty()) {
        System.out.println("Aucune dette non soldée trouvée.");
    } else {
        dettes.forEach(dette -> {
            System.out.println("Dette ID: " + dette.getId() + ", Montant restant: " + dette.getMontantRestant());
        });
    }
}

    // Faire une demande de dette
    // public void demanderDette() {
    //     System.out.println("\n=== Faire une demande de dette ===");
    //     System.out.print("Montant demandé : ");
    //     double montant = scanner.nextDouble();
    //     scanner.nextLine(); // Consomme la nouvelle ligne

    //     if (detteService.demanderDette(clientService.getCurrentClient(), montant)) {
    //         System.out.println("Demande de dette effectuée avec succès.");
    //     } else {
    //         System.out.println("Échec de la demande de dette.");
    //     }
    // }

    public void demanderDette() {
        System.out.println("\n=== Faire une demande de dette ===");
        Client currentClient = clientService.getCurrentClient();
    
        if (currentClient == null) {
            System.out.println("Erreur : aucun client connecté. Veuillez vous reconnecter.");
            return;
        }
    
        System.out.print("Montant demandé : ");
        double montant = scanner.nextDouble();
        scanner.nextLine(); // Consomme la nouvelle ligne
    
        if (detteService.demanderDette(currentClient, montant)) {
            System.out.println("Demande de dette effectuée avec succès.");
        } else {
            System.out.println("Échec de la demande de dette.");
        }
    }
    

        // Lister les demandes de dette pour le client actuel
        public void listerDemandesDettePourClient() {
            System.out.println("\n=== Mes demandes de dette ===");
            List<Dette> demandes = detteService.listerDemandesPourClient(clientService.getCurrentClient());
            if (demandes.isEmpty()) {
                System.out.println("Aucune demande de dette trouvée.");
            } else {
                demandes.forEach(demande -> {
                    System.out.println("Demande ID: " + demande.getId() + ", État: " + demande.getEtat());
                });
            }
        }

        // Relancer une demande de dette annulée
        public void relancerDemandeDette() {
            System.out.println("\n=== Relancer une demande de dette annulée ===");
            System.out.print("ID de la demande à relancer : ");
            int demandeId = scanner.nextInt();
            scanner.nextLine(); // Consomme la nouvelle ligne

            if (detteService.relancerDemande(clientService.getCurrentClient(), demandeId)) {
                System.out.println("Demande de dette relancée avec succès.");
            } else {
                System.out.println("Échec de la relance de la demande.");
            }
        }

}
