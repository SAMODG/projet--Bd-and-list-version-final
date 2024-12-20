package com.monsite.gestioncahierdette.repositories.ripo;




import java.util.List;

import com.monsite.gestioncahierdette.entity.Client;
import com.monsite.gestioncahierdette.entity.Dette;

public interface DetteRepository {
    void ajouter(Dette dette);
    List<Dette> trouverDettesParClient(Client client);
    List<Dette> trouverDettesNonSoldeesParClient(Client client);
    List<Dette> listerTous(); // Ajouter cette méthode
    void mettreAJour(Dette dette); // Nouvelle méthode pour mettre à jour une dett

    List<Dette> listerParClientEtEtat(Client client, boolean estSoldee);

    boolean creerDemande(Client client, double montant);

    List<Dette> listerDemandesParClient(Client client);

    Dette trouverParId(int id);

}


