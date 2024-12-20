package com.monsite.gestioncahierdette.repositories.bd;

import com.monsite.gestioncahierdette.core.bd.DatabaseImpl;
import com.monsite.gestioncahierdette.entity.Client;
import com.monsite.gestioncahierdette.entity.Dette;
import com.monsite.gestioncahierdette.repositories.ripo.DetteRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetteRepositoryBd extends DatabaseImpl implements DetteRepository {

    @Override
    public void ajouter(Dette dette) {
        String sql = "INSERT INTO dettes (client_id, montantTotal, montantRestant, date) VALUES (?, ?, ?, ?)";
        try {
            initPreparedStatement(sql);
            statement.setInt(1, dette.getClient().getId());  // Assurez-vous que l'objet Client a un ID valide
            statement.setDouble(2, dette.getMontantTotal());
            statement.setDouble(3, dette.getMontantRestant());
            statement.setDate(4, new java.sql.Date(dette.getDate().getTime()));
            executeUpdate();

            // Récupération de l'ID généré pour l'objet Dette
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                dette.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
    }

    @Override
    public List<Dette> trouverDettesParClient(Client client) {
        String sql = "SELECT * FROM dettes WHERE client_id = ?";
        List<Dette> dettes = new ArrayList<>();
        try {
            initPreparedStatement(sql);
            statement.setInt(1, client.getId());
            ResultSet rs = executeSelect();
            while (rs.next()) {
                Dette dette = new Dette();
                dette.setId(rs.getInt("id"));
                dette.setClient(client);
                dette.setMontantTotal(rs.getDouble("montantTotal"));
                dette.setMontantRestant(rs.getDouble("montantRestant"));
                dette.setDate(rs.getDate("date"));
                dettes.add(dette);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
        return dettes;
    }

    @Override
    public List<Dette> trouverDettesNonSoldeesParClient(Client client) {
        String sql = "SELECT * FROM dettes WHERE client_id = ? AND montantRestant > 0";
        List<Dette> dettes = new ArrayList<>();
        try {
            initPreparedStatement(sql);
            statement.setInt(1, client.getId());
            ResultSet rs = executeSelect();
            while (rs.next()) {
                Dette dette = new Dette();
                dette.setId(rs.getInt("id"));
                dette.setClient(client);
                dette.setMontantTotal(rs.getDouble("montantTotal"));
                dette.setMontantRestant(rs.getDouble("montantRestant"));
                dette.setDate(rs.getDate("date"));
                dettes.add(dette);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
        return dettes;
    }

    @Override
    public List<Dette> listerTous() {
        String sql = "SELECT * FROM dettes";
        List<Dette> dettes = new ArrayList<>();
        try {
            initPreparedStatement(sql);
            ResultSet rs = executeSelect();
            while (rs.next()) {
                Dette dette = new Dette();
                dette.setId(rs.getInt("id"));
                
                // Ici, vous devrez potentiellement récupérer le client par son ID
                Client client = new Client();
                client.setId(rs.getInt("client_id"));
                dette.setClient(client);
                
                dette.setMontantTotal(rs.getDouble("montantTotal"));
                dette.setMontantRestant(rs.getDouble("montantRestant"));
                dette.setDate(rs.getDate("date"));
                dettes.add(dette);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
        return dettes;
    }

    // @Override
    // public void mettreAJour(Dette dette) {
    //     String sql = "UPDATE dettes SET montantRestant = ? WHERE id = ?";
    //     try {
    //         initPreparedStatement(sql);
    //         statement.setDouble(1, dette.getMontantRestant());
    //         statement.setInt(2, dette.getId());
    //         executeUpdate();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     } finally {
    //         closeConnexion();
    //     }
    // }

    @Override
    public void mettreAJour(Dette dette) {
        String sql = "UPDATE dettes SET montantTotal = ?, montantRestant = ?, estSoldee = ?, etat = ? WHERE id = ?";
        try {
            initPreparedStatement(sql);
            statement.setDouble(1, dette.getMontantTotal());
            statement.setDouble(2, dette.getMontantRestant());
            statement.setBoolean(3, dette.isEstSoldee());
            statement.setString(4, dette.getEtat());
            statement.setInt(5, dette.getId());
            executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
    }



    @Override
    public List<Dette> listerParClientEtEtat(Client client, boolean estSoldee) {
        String sql = "SELECT * FROM dettes WHERE client_id = ? AND estSoldee = ?";
        List<Dette> dettes = new ArrayList<>();

        try {
            initPreparedStatement(sql);
            statement.setInt(1, client.getId());
            statement.setBoolean(2, estSoldee);
            ResultSet rs = executeSelect();

            while (rs.next()) {
                Dette dette = new Dette();
                dette.setId(rs.getInt("id"));
                dette.setMontantTotal(rs.getDouble("montantTotal"));
                dette.setMontantRestant(rs.getDouble("montantRestant"));
                dette.setEstSoldee(rs.getBoolean("estSoldee"));
                dettes.add(dette);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }

        return dettes;
    }

    // @Override
    // public boolean creerDemande(Client client, double montant) {
    //     String sql = "INSERT INTO dettes (client_id, montantTotal, montantRestant, estSoldee, etat) VALUES (?, ?, ?, ?, ?)";
    //     try {
    //         initPreparedStatement(sql);
    //         statement.setInt(1, client.getId());
    //         statement.setDouble(2, montant);
    //         statement.setDouble(3, montant);
    //         statement.setBoolean(4, false); // Non soldée par défaut
    //         statement.setString(5, "EN COURS"); // État initial

    //         executeUpdate();
    //         return true;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     } finally {
    //         closeConnexion();
    //     }
    //     return false;
    // }

        @Override
        public boolean creerDemande(Client client, double montant) {
            if (client == null) {
                throw new IllegalArgumentException("Le client ne peut pas être nul.");
            }

            String sql = "INSERT INTO demandes (client_id, montant, etat) VALUES (?, ?, ?)";
            try {
                initPreparedStatement(sql);
                statement.setInt(1, client.getId());
                statement.setDouble(2, montant);
                statement.setString(3, "EN COURS");
                executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnexion();
            }
            return false;
        }


    @Override
    public List<Dette> listerDemandesParClient(Client client) {
        String sql = "SELECT * FROM dettes WHERE client_id = ?";
        List<Dette> demandes = new ArrayList<>();

        try {
            initPreparedStatement(sql);
            statement.setInt(1, client.getId());
            ResultSet rs = executeSelect();

            while (rs.next()) {
                Dette demande = new Dette();
                demande.setId(rs.getInt("id"));
                demande.setMontantTotal(rs.getDouble("montantTotal"));
                demande.setMontantRestant(rs.getDouble("montantRestant"));
                demande.setEtat(rs.getString("etat"));
                demandes.add(demande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }

        return demandes;
    }

    @Override
    public Dette trouverParId(int id) {
        String sql = "SELECT * FROM dettes WHERE id = ?";
        try {
            initPreparedStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = executeSelect();

            if (rs.next()) {
                Dette dette = new Dette();
                dette.setId(rs.getInt("id"));
                dette.setMontantTotal(rs.getDouble("montantTotal"));
                dette.setMontantRestant(rs.getDouble("montantRestant"));
                dette.setEtat(rs.getString("etat"));
                dette.setEstSoldee(rs.getBoolean("estSoldee"));
                return dette;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
        return null;
    }

    
}


