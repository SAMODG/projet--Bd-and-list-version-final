package com.monsite.gestioncahierdette.repositories.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.monsite.gestioncahierdette.entity.Dette;
import com.monsite.gestioncahierdette.entity.Paiement;
import com.monsite.gestioncahierdette.repositories.ripo.PaiementRepository;

public class PaiementRepositoryImpl implements PaiementRepository {

    private List<Paiement> paiements = new ArrayList<>();

    @Override
    public void ajouter(Paiement paiement) {
        paiements.add(paiement);
    }

    @Override
    public List<Paiement> trouverPaiementsParDette(Dette dette) {
        return paiements.stream()
                        .filter(paiement -> paiement.getDette().equals(dette))
                        .collect(Collectors.toList());
    }
}
