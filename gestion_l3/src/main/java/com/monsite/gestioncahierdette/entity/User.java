package com.monsite.gestioncahierdette.entity;

import lombok.Data;

@Data
public class User {
    private int id; // Ajout de l'attribut id
    private String email;
    private String login;
    private String password;
    private Role role;
    private boolean isActive; // Champ pour indiquer l'état actif/inactif


      // Constructeur sans arguments (nécessaire pour instancier un objet vide)
      public User() {}

    // Constructeur avec tous les attributs
    public User(int id, String email, String login, String password, Role role, boolean isActive) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    // Constructeur sans l'id pour les nouveaux utilisateurs
    public User(String email, String login, String password, Role role) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
        this.isActive = true; // Par défaut, l'utilisateur est actif lors de la création
    }

    public enum Role {
        ADMIN,
        BOUTIQUIER,
        CLIENT
    }
}
