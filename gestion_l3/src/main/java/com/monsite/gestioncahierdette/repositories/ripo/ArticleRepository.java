package com.monsite.gestioncahierdette.repositories.ripo;




import java.util.List;

import com.monsite.gestioncahierdette.entity.Article;

public interface ArticleRepository {
    void ajouter(Article article);
    List<Article> listerDisponibles();
    Article trouverParNom(String nom);
    void mettreAJourStock(Article article, int nouvelleQuantite);
    void mettreAJour(Article article);
}
