package com.monsite.gestioncahierdette.repositories.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.monsite.gestioncahierdette.entity.User;
import com.monsite.gestioncahierdette.repositories.ripo.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    private List<User> users = new ArrayList<>();

    @Override
    public void ajouter(User user) {
        users.add(user);
    }

    @Override
    public User trouverParLogin(String login) {
        return users.stream()
                    .filter(user -> user.getLogin().equals(login))
                    .findFirst()
                    .orElse(null);
    }

    @Override
    public List<User> listerParRoleEtStatut(boolean actif, String role) {
        return users.stream()
                    .filter(user -> user.getRole().toString().equals(role) && user.isActive() == actif)
                    .collect(Collectors.toList());
    }

    @Override
    public void mettreAJour(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user); // Remplace l'utilisateur existant avec les nouvelles informations
                break;
            }
        }
    }



    @Override
    public User trouverParId(int userId) {
        return users.stream()
                    .filter(user -> user.getId() == userId)
                    .findFirst()
                    .orElse(null);
    }
    
}
