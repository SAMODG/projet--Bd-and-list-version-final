package com.monsite.gestioncahierdette.views;

import com.monsite.gestioncahierdette.services.ArticleService;


import java.util.Scanner;

public class ArticleView {

    private ArticleService articleService;
    private Scanner scanner;

    public ArticleView(ArticleService articleService, Scanner scanner) {
        this.articleService = articleService;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        System.out.println("\n=== Gestion des Articles ===");
        System.out.println("1. Créer un article");
        System.out.println("2. Lister les articles disponibles");
        System.out.println("3. Mettre à jour le stock d'un article");
        System.out.print("Choisissez une option : ");

        int choix = scanner.nextInt();
        scanner.nextLine();  // Consomme la nouvelle ligne

        switch (choix) {
            case 1:
                creerArticle();
                break;
            case 2:
                listerArticlesDisponibles();
                break;
            case 3:
                mettreAJourStock();
                break;
            default:
                System.out.println("Option invalide.");
        }
    }



    
    public void creerArticle() {
        System.out.println("\n=== Créer un nouvel article ===");
        System.out.print("Nom de l'article : ");
        String nom = scanner.nextLine();
        System.out.print("Quantité en stock : ");
        int qteStock = scanner.nextInt();
        scanner.nextLine(); // Consomme la nouvelle ligne
    
        articleService.creerArticle(nom, qteStock);
        System.out.println("Article créé avec succès.");
    }
    


    
    public void listerArticlesDisponibles() {
        System.out.println("\n=== Liste des articles disponibles ===");
        var articles = articleService.listerArticlesDisponibles();
    
        if (articles.isEmpty()) {
            System.out.println("Aucun article disponible.");
        } else {
            articles.forEach(article -> System.out.println("Nom : " + article.getNom() + ", Stock : " + article.getQteStock()));
        }
    }


 



        public void mettreAJourStock() {
            System.out.println("\n=== Mettre à jour le stock d'un article ===");
            System.out.print("Nom de l'article : ");
            String nom = scanner.nextLine();
            System.out.print("Nouvelle quantité en stock : ");
            int nouvelleQuantite = scanner.nextInt();
            scanner.nextLine(); // Consomme la nouvelle ligne
        
            boolean success = articleService.mettreAJourStock(nom, nouvelleQuantite);
        
            if (success) {
                System.out.println("Le stock de l'article a été mis à jour avec succès.");
            } else {
                System.out.println("Erreur : Article introuvable.");
            }
        }


        
    
}
