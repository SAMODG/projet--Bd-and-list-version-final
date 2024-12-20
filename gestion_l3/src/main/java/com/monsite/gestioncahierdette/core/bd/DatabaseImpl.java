package com.monsite.gestioncahierdette.core.bd;

import java.sql.*;


public class DatabaseImpl implements Database {
    protected Connection connection;
    protected PreparedStatement statement; // Attribut renommé en `statement` pour éviter la confusion avec le type `Statement`
    private static final String url = "jdbc:mysql://localhost:3306/gestion_cahier_dette4"; 
    private static final String username = "root"; // Remplacez par votre nom d'utilisateur
    private static final String password = ""; // Remplacez par votre mot de passe

    @Override
    public void openConnexion() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connexion réussie à la base de données.");
        } catch (SQLException e) {
            e.printStackTrace(); // Affiche les erreurs SQL
            System.out.println("Échec de la connexion : " + e.getMessage());
        }
    }

    @Override
    public void closeConnexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection. close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Affiche les erreurs SQL
        }
    }

    @Override
    public ResultSet executeSelect() throws SQLException {           
           
            return statement.executeQuery();
               
    }

    @Override
    public int executeUpdate() throws SQLException {
         
            return statement.executeUpdate();
    }

    @Override
    public void initPreparedStatement(String sql) throws SQLException {
        this.openConnexion();
        if (sql.trim().toLowerCase().startsWith("inser")) {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
            statement = connection.prepareStatement(sql);
        
        }
    }


    public boolean testerConnexion() {
        String sql = "SELECT 1"; // Requête SQL simple qui retourne toujours 1
        try {
            initPreparedStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next() && rs.getInt(1) == 1) {
                System.out.println("Connexion réussie à la base de données.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        } finally {
            closeConnexion();
        }
        return false;
    }
    
        
}
