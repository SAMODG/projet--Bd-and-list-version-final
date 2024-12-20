package com.monsite.gestioncahierdette.repositories.impl;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.monsite.gestioncahierdette.entity.Article;
import com.monsite.gestioncahierdette.repositories.ripo.ArticleRepository;

public class ArticleRepositoryImpl implements ArticleRepository {

    private List<Article> articles = new ArrayList<>();

    @Override
    public void ajouter(Article article) {
        articles.add(article);
    }

    @Override
    public List<Article> listerDisponibles() {
        return articles.stream()
                       .filter(article -> article.getQteStock() > 0)
                       .collect(Collectors.toList());
    }

    @Override
    public void mettreAJourStock(Article article, int nouvelleQuantite) {
        article.setQteStock(nouvelleQuantite);
    }



    @Override
    public Article trouverParNom(String nom) {
        for (Article article : articles) {
            if (article.getNom().equalsIgnoreCase(nom)) {
                return article;
            }
        }
        return null;
    }

    @Override
    public void mettreAJour(Article article) {
        for (Article a : articles) {
            if (a.getNom().equalsIgnoreCase(article.getNom())) {
                a.setQteStock(article.getQteStock());
                break;
            }
        }
    }

    // Ajout d'une méthode pour initialiser des articles en mémoire
    public void ajouterArticle(Article article) {
        articles.add(article);
    }


}
