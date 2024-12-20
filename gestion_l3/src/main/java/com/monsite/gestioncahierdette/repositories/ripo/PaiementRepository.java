package com.monsite.gestioncahierdette.repositories.ripo;

import java.util.List;

import com.monsite.gestioncahierdette.entity.Dette;
import com.monsite.gestioncahierdette.entity.Paiement;

public interface PaiementRepository {
    void ajouter(Paiement paiement);
    List<Paiement> trouverPaiementsParDette(Dette dette);
}
