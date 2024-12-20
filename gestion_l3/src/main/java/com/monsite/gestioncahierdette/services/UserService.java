package com.monsite.gestioncahierdette.services;

import java.util.List;
import com.monsite.gestioncahierdette.entity.User;
import com.monsite.gestioncahierdette.entity.User.Role; // Utilisation de User.Role

public interface UserService {

    // Créer un compte utilisateur pour un client existant
    void creerCompteUtilisateurPourClient(String email, String login, String password, Role role);

    // Activer le commpte 
    void activerCompte(User user);

    // Désactiver le compte 
    void desactiverCompte(User user);


    // Désactiver un compte utilisateur
    void creerCompteUtilisateurAvecRole(String email, String login, String password, Role role);

    // Lister les comptes utilisateurs actifs ou filtrés par rôle
    List<User> listerComptes(boolean actifs, Role role);

    // Trouver un utilisateur par son login
    User trouverParLogin(String login);

    User trouverParId(int userId);


    
}
