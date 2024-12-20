package com.monsite.gestioncahierdette.repositories.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.monsite.gestioncahierdette.entity.Client;
import com.monsite.gestioncahierdette.repositories.ripo.ClientRepository;

public class ClientRepositoryImpl implements ClientRepository {

    private List<Client> clients = new ArrayList<>();

    @Override
    public void ajouter(Client client) {
        clients.add(client);
    }

    @Override
    public Client trouverParTelephone(String telephone) {
        return clients.stream()
                      .filter(client -> client.getTelephone().equals(telephone))
                      .findFirst()
                      .orElse(null);
    }

    @Override
    public List<Client> listerTous() {
        return new ArrayList<>(clients);
    }

    @Override
    public List<Client> listerAvecCompte(boolean avecCompte) {
        return clients.stream()
                      .filter(client -> (avecCompte && client.getUser() != null) || (!avecCompte && client.getUser() == null))
                      .collect(Collectors.toList());
    }
}
