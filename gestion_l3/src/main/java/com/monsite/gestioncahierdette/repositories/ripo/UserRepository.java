package com.monsite.gestioncahierdette.repositories.ripo;




import java.util.List;

import com.monsite.gestioncahierdette.entity.User;

public interface UserRepository {
    void ajouter(User user);
    User trouverParLogin(String login);
    List<User> listerParRoleEtStatut(boolean actif, String role);
    void mettreAJour(User user); // Nouvelle méthode pour mettre à jour un utilisateur
    User trouverParId(int userId);

  
    
}
