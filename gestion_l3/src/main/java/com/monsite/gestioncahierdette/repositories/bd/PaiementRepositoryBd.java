package com.monsite.gestioncahierdette.repositories.bd;

import com.monsite.gestioncahierdette.core.bd.DatabaseImpl;
import com.monsite.gestioncahierdette.entity.Dette;
import com.monsite.gestioncahierdette.entity.Paiement;
import com.monsite.gestioncahierdette.repositories.ripo.PaiementRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaiementRepositoryBd extends DatabaseImpl implements PaiementRepository {

    @Override
    public void ajouter(Paiement paiement) {
        String sql = "INSERT INTO paiements (dette_id, date, montant) VALUES (?, ?, ?)";
        try {
            initPreparedStatement(sql);
            statement.setInt(1, paiement.getDette().getId());  // Assurez-vous que l'objet Dette a un ID valide
            statement.setDate(2, new java.sql.Date(paiement.getDate().getTime()));
            statement.setDouble(3, paiement.getMontant());
            executeUpdate();

            // Récupération de l'ID généré pour l'objet Paiement
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                paiement.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
    }

    @Override
    public List<Paiement> trouverPaiementsParDette(Dette dette) {
        String sql = "SELECT * FROM paiements WHERE dette_id = ?";
        List<Paiement> paiements = new ArrayList<>();
        try {
            initPreparedStatement(sql);
            statement.setInt(1, dette.getId());
            ResultSet rs = executeSelect();
            while (rs.next()) {
                Paiement paiement = new Paiement();
                paiement.setId(rs.getInt("id"));
                paiement.setDate(rs.getDate("date"));
                paiement.setMontant(rs.getDouble("montant"));
                paiement.setDette(dette);  // Associer l'objet Dette
                paiements.add(paiement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
        return paiements;
    }
}
