package com.monsite.gestioncahierdette.repositories.bd;



import com.monsite.gestioncahierdette.core.bd.DatabaseImpl;
import com.monsite.gestioncahierdette.entity.User;
import com.monsite.gestioncahierdette.repositories.ripo.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryBd extends DatabaseImpl implements UserRepository {

    // Méthode dans UserRepositoryBd
@Override
public void ajouter(User user) {
    String sql = "INSERT INTO users (email, login, password, role, is_active) VALUES (?, ?, ?, ?, ?)";
    try {
        initPreparedStatement(sql);
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getLogin());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getRole().toString());
        statement.setBoolean(5, true);
        executeUpdate();

        // Récupération de l'ID généré et assignation
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            user.setId(generatedKeys.getInt(1));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnexion();
    }
}

    @Override
    public void mettreAJour(User user) {
        String sql = "UPDATE users SET login = ?, password = ?, role = ?, is_active = ? WHERE id = ?";
        try {
            initPreparedStatement(sql);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toString());
            statement.setBoolean(4, user.isActive());
            statement.setInt(5, user.getId());
            executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
    }


    @Override
    public User trouverParLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";
        User user = null;
        try {
            initPreparedStatement(sql);
            statement.setString(1, login);
            ResultSet rs = executeSelect();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setRole(User.Role.valueOf(rs.getString("role")));
                user.setActive(rs.getBoolean("is_active"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
        return user;
    }

    @Override
    public List<User> listerParRoleEtStatut(boolean actif, String role) {
        String sql = "SELECT * FROM users WHERE role = ? AND is_active = ?";
        List<User> users = new ArrayList<>();
        try {
            initPreparedStatement(sql);
            statement.setString(1, role);
            statement.setBoolean(2, actif);
            ResultSet rs = executeSelect();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setRole(User.Role.valueOf(rs.getString("role")));
                user.setActive(rs.getBoolean("is_active"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnexion();
        }
        return users;
    }


        @Override
        public User trouverParId(int userId) {
            User user = null;
            String sql = "SELECT * FROM users WHERE id = ?";
            try {
                initPreparedStatement(sql);
                statement.setInt(1, userId);
                ResultSet rs = executeSelect();
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setLogin(rs.getString("login"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(User.Role.valueOf(rs.getString("role")));
                    user.setActive(rs.getBoolean("is_active"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnexion();
            }
            return user;
        }
    

}
