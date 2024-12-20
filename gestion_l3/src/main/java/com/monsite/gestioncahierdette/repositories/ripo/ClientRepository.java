package com.monsite.gestioncahierdette.repositories.ripo;


import java.util.List;

import com.monsite.gestioncahierdette.entity.Client;

public interface ClientRepository {
    
    void ajouter(Client client);
    Client trouverParTelephone(String telephone);
    List<Client> listerTous();
    List<Client> listerAvecCompte(boolean avecCompte);

}
