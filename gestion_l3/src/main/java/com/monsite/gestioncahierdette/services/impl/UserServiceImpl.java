package com.monsite.gestioncahierdette.services.impl;


import com.monsite.gestioncahierdette.entity.User;
import com.monsite.gestioncahierdette.entity.User.Role;
import com.monsite.gestioncahierdette.repositories.ripo.UserRepository;
import com.monsite.gestioncahierdette.services.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void creerCompteUtilisateurPourClient(String email, String login, String password, Role role) {
        User user = new User(email, login, password, role);
        userRepository.ajouter(user); // Enregistre l'utilisateur en base
    }

       
    
            @Override
        public void activerCompte(User user) {
            if (user == null) {
                throw new IllegalArgumentException("L'utilisateur est null.");
            }

            if (user.isActive()) {
                System.out.println("L'utilisateur est déjà actif.");
            } else {
                user.setActive(true);
                userRepository.mettreAJour(user); // Mise à jour dans le dépôt
                System.out.println("Utilisateur activé avec succès.");
            }
        }

        @Override
        public void desactiverCompte(User user) {
            if (user == null) {
                throw new IllegalArgumentException("L'utilisateur est null.");
            }

            if (!user.isActive()) {
                System.out.println("L'utilisateur est déjà désactivé.");
            } else {
                user.setActive(false);
                userRepository.mettreAJour(user); // Mise à jour dans le dépôt
                System.out.println("Utilisateur désactivé avec succès.");
            }
        }



    @Override
    public List<User> listerComptes(boolean actifs, Role role) {
        return userRepository.listerParRoleEtStatut(actifs, role.toString()); // Utilisation du repository pour filtrer
    }


    @Override
    public User trouverParLogin(String login) {
        return userRepository.trouverParLogin(login);
    }

    
        @Override
    public void creerCompteUtilisateurAvecRole(String email, String login, String password, Role role) {
        if (email == null || login == null || password == null || role == null) {
            throw new IllegalArgumentException("Les informations utilisateur sont incomplètes.");
        }

        // Vérifie si un utilisateur avec ce login existe déjà
        User existingUser = userRepository.trouverParLogin(login);
        if (existingUser != null) {
            throw new IllegalArgumentException("Un utilisateur avec ce login existe déjà.");
        }

        // Création du nouvel utilisateur
        User user = new User(email, login, password, role);
        userRepository.ajouter(user);
        System.out.println("Utilisateur avec rôle " + role + " créé avec succès.");
    }


    @Override
    public User trouverParId(int userId) {
        return userRepository.trouverParId(userId);
    }


}
