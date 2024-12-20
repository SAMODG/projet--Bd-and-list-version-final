package com.monsite.gestioncahierdette.services.impl;

import com.monsite.gestioncahierdette.entity.Article;
import com.monsite.gestioncahierdette.repositories.ripo.ArticleRepository;
import com.monsite.gestioncahierdette.services.ArticleService;

import java.util.List;

public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


   

    @Override
    public void creerArticle(String nom, int qteStock) {
        Article article = new Article(nom, qteStock);
        articleRepository.ajouter(article);
    }

            @Override
    public List<Article> listerArticlesDisponibles() {
        return articleRepository.listerDisponibles();
    }

    @Override
    public boolean mettreAJourStock(String nom, int nouvelleQuantite) {
        Article article = articleRepository.trouverParNom(nom); // Vérifiez que trouverParNom(nom) est correctement implémentée.
    
        if (article != null) {
            article.setQteStock(nouvelleQuantite); // Vérifiez que la méthode setQteStock() existe et est publique.
            articleRepository.mettreAJour(article); // Vérifiez que la méthode mettreAJour() est correctement implémentée.
            return true;
        }
    
        return false;
    }
    
}
