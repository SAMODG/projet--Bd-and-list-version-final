package com.monsite.gestioncahierdette.repositories.bd;

import com.monsite.gestioncahierdette.core.bd.DatabaseImpl;
import com.monsite.gestioncahierdette.entity.Article;
import com.monsite.gestioncahierdette.repositories.ripo.ArticleRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepositoryBd extends DatabaseImpl implements ArticleRepository {

    @Override
    public void ajouter(Article article) {
        String sql = "INSERT INTO articles (nom, qteStock) VALUES (?, ?)";
        try {
            initPreparedStatement(sql);
            statement.setString(1, article.getNom());
            statement.setInt(2, article.getQteStock());
            executeUpdate();

            // Récupération de l'ID généré
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                article.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
    }

    @Override
    public List<Article> listerDisponibles() {
        String sql = "SELECT * FROM articles WHERE qteStock > 0";
        List<Article> articles = new ArrayList<>();
        try {
            initPreparedStatement(sql);
            ResultSet rs = executeSelect();
            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setNom(rs.getString("nom"));
                article.setQteStock(rs.getInt("qteStock"));
                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
        return articles;
    }

    @Override
    public void mettreAJourStock(Article article, int nouvelleQuantite) {
        String sql = "UPDATE articles SET qteStock = ? WHERE id = ?";
        try {
            initPreparedStatement(sql);
            statement.setInt(1, nouvelleQuantite);
            statement.setInt(2, article.getId()); // Assurez-vous que article.getId() renvoie un ID valide
            executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
    }



     // Ajout de la méthode trouverParNom
     @Override
     public Article trouverParNom(String nom) {
         String sql = "SELECT * FROM articles WHERE nom = ?";
         try {
             initPreparedStatement(sql);
             statement.setString(1, nom);
             ResultSet rs = executeSelect();
             if (rs.next()) {
                 Article article = new Article();
                 article.setId(rs.getInt("id"));
                 article.setNom(rs.getString("nom"));
                 article.setQteStock(rs.getInt("qteStock"));
                 return article;
             }
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             closeConnexion();
         }
         return null;
     }
 
     // Ajout de la méthode mettreAJour
     @Override
     public void mettreAJour(Article article) {
         String sql = "UPDATE articles SET nom = ?, qteStock = ? WHERE id = ?";
         try {
             initPreparedStatement(sql);
             statement.setString(1, article.getNom());
             statement.setInt(2, article.getQteStock());
             statement.setInt(3, article.getId());
             executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             closeConnexion();
         }
     }

 }


