package com.monsite.gestioncahierdette.services;


import java.util.List;

import com.monsite.gestioncahierdette.entity.Article;

public interface ArticleService {

    // Créer un nouvel article
    void creerArticle(String nom, int qteStock);

    // Lister les articles disponibles (qteStock > 0)
    List<Article> listerArticlesDisponibles();

    // Mettre à jour la quantité en stock d’un article
    boolean mettreAJourStock(String nom, int nouvelleQuantite);

    
}
