package com.monsite.gestioncahierdette.repositories.bd;



import com.monsite.gestioncahierdette.core.bd.DatabaseImpl;
import com.monsite.gestioncahierdette.entity.Client;
import com.monsite.gestioncahierdette.repositories.ripo.ClientRepository;
import com.monsite.gestioncahierdette.entity.Adresse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryBd extends DatabaseImpl implements ClientRepository {

    @Override
    public void ajouter(Client client) {
        String sql = "INSERT INTO clients (surname, telephone, rue, ville, codePostal, pays, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            initPreparedStatement(sql);
            statement.setString(1, client.getSurname());
            statement.setString(2, client.getTelephone());
            statement.setString(3, client.getAdresse().getRue());
            statement.setString(4, client.getAdresse().getVille());
            statement.setString(5, client.getAdresse().getCodePostal());
            statement.setString(6, client.getAdresse().getPays());
            if (client.getUser() != null) {
                statement.setInt(7, client.getUser().getId()); // Si un compte utilisateur est associé
            } else {
                statement.setNull(7, java.sql.Types.INTEGER); // Définit user_id sur NULL si aucun compte utilisateur n'est associé
            }
            executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
    }
    
    @Override
    public Client trouverParTelephone(String telephone) {
        String sql = "SELECT * FROM clients WHERE telephone = ?";
        Client client = null;
        try {
            initPreparedStatement(sql);
            statement.setString(1, telephone);
            ResultSet rs = executeSelect();
            if (rs.next()) {
                Adresse adresse = new Adresse(
                        rs.getString("rue"),
                        rs.getString("ville"),
                        rs.getString("codePostal"),
                        rs.getString("pays")
                );
                client = new Client(
                        rs.getInt("id"),
                        rs.getString("surname"),
                        rs.getString("telephone"),
                        adresse
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
        return client;
    }

    @Override
    public List<Client> listerTous() {
        String sql = "SELECT * FROM clients";
        List<Client> clients = new ArrayList<>();
        try {
            initPreparedStatement(sql);
            ResultSet rs = executeSelect();
            while (rs.next()) {
                Adresse adresse = new Adresse(
                        rs.getString("rue"),
                        rs.getString("ville"),
                        rs.getString("codePostal"),
                        rs.getString("pays")
                );
                Client client = new Client(
                        rs.getInt("id"),
                        rs.getString("surname"),
                        rs.getString("telephone"),
                        adresse
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
        return clients;
    }

    @Override
    public List<Client> listerAvecCompte(boolean avecCompte) {
        String sql = avecCompte 
                ? "SELECT * FROM clients WHERE user_id IS NOT NULL" 
                : "SELECT * FROM clients WHERE user_id IS NULL";
        List<Client> clients = new ArrayList<>();
        try {
            initPreparedStatement(sql);
            ResultSet rs = executeSelect();
            while (rs.next()) {
                Adresse adresse = new Adresse(
                        rs.getString("rue"),
                        rs.getString("ville"),
                        rs.getString("codePostal"),
                        rs.getString("pays")
                );
                Client client = new Client(
                        rs.getInt("id"),
                        rs.getString("surname"),
                        rs.getString("telephone"),
                        adresse
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
        return clients;
    }
}
