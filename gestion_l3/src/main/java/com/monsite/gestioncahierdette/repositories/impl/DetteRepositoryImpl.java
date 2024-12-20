package com.monsite.gestioncahierdette.repositories.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.monsite.gestioncahierdette.entity.Client;
import com.monsite.gestioncahierdette.entity.Dette;
import com.monsite.gestioncahierdette.repositories.ripo.DetteRepository;

public class DetteRepositoryImpl implements DetteRepository {

    private List<Dette> dettes = new ArrayList<>();

    @Override
    public void ajouter(Dette dette) {
        dettes.add(dette);
    }

    @Override
    public List<Dette> trouverDettesParClient(Client client) {
        return dettes.stream()
                     .filter(dette -> dette.getClient().equals(client))
                     .collect(Collectors.toList());
    }

    @Override
    public List<Dette> trouverDettesNonSoldeesParClient(Client client) {
        return trouverDettesParClient(client).stream()
                     .filter(dette -> dette.getMontantRestant() > 0)
                     .collect(Collectors.toList());
    }

            @Override
        public List<Dette> listerTous() {
            return new ArrayList<>(dettes); // Retourne une copie de la liste de dettes
        }


        // @Override
        // public void mettreAJour(Dette dette) {
        //     for (int i = 0; i < dettes.size(); i++) {
        //         if (dettes.get(i).getId() == dette.getId()) { // Recherche de la dette par ID
        //             dettes.set(i, dette); // Remplace la dette existante par la version mise à jour
        //             break;
        //         }
        //     }
        // }
        
        @Override
            public void mettreAJour(Dette dette) {
                for (int i = 0; i < dettes.size(); i++) {
                    if (dettes.get(i).getId() == dette.getId()) {
                        dettes.set(i, dette);
                        return;
                    }
                }
            }
        


                @Override
            public List<Dette> listerParClientEtEtat(Client client, boolean estSoldee) {
                List<Dette> result = new ArrayList<>();
                for (Dette dette : dettes) {
                    if (dette.getClient().equals(client) && dette.isArchive() == estSoldee) {
                        result.add(dette);
                    }
                }
                return result;
            }

            @Override
            public boolean creerDemande(Client client, double montant) {
                try {
                    Dette demande = new Dette();
                    demande.setClient(client);
                    demande.setMontantTotal(montant);
                    demande.setMontantRestant(montant);
                    demande.setEtat("EN COURS"); // Définit l'état initial
                    demande.setArchive(false); // Les nouvelles dettes ne sont pas archivées par défaut
            
                    dettes.add(demande); // Ajoute la dette dans la liste simulée
                    return true; // Retourne true si tout se passe bien
                } catch (Exception e) {
                    e.printStackTrace(); // Log de l'exception
                    return false; // Retourne false en cas d'erreur
                }
            }
            
            @Override
            public List<Dette> listerDemandesParClient(Client client) {
                List<Dette> result = new ArrayList<>();
                for (Dette dette : dettes) {
                    if (dette.getClient().equals(client) && "EN COURS".equalsIgnoreCase(dette.getEtat())) {
                        result.add(dette);
                    }
                }
                return result;
            }

            @Override
            public Dette trouverParId(int id) {
                for (Dette dette : dettes) {
                    if (dette.getId() == id) {
                        return dette;
                    }
                }
                return null;
            }

            
}
