package com.monsite.gestioncahierdette.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.monsite.gestioncahierdette.entity.Article;
import com.monsite.gestioncahierdette.entity.Client;
import com.monsite.gestioncahierdette.entity.Dette;
import com.monsite.gestioncahierdette.entity.Paiement;
import com.monsite.gestioncahierdette.repositories.ripo.DetteRepository;
import com.monsite.gestioncahierdette.repositories.ripo.PaiementRepository;
import com.monsite.gestioncahierdette.services.DetteService;

public class DetteServiceImpl implements DetteService {

    private final DetteRepository detteRepository;
    private final PaiementRepository paiementRepository;

    public DetteServiceImpl(DetteRepository detteRepository, PaiementRepository paiementRepository) {
        this.detteRepository = detteRepository;
        this.paiementRepository = paiementRepository;
    }

    @Override
    public void creerDette(Client client, Date date, double montant, List<Article> articles) {
        Dette dette = new Dette(client, date, montant, articles);
        client.getDettes().add(dette);
        detteRepository.ajouter(dette); // Enregistrement de la dette en base de données via le repository
    }

    @Override
    public void enregistrerPaiement(Dette dette, Date date, double montant) {
        Paiement paiement = new Paiement(date, montant, dette);
        paiementRepository.ajouter(paiement); // Enregistrement du paiement dans la base de données

        // Met à jour le montant restant de la dette après paiement
        double nouveauMontantRestant = dette.getMontantRestant() - montant;
        dette.setMontantRestant(Math.max(0, nouveauMontantRestant));
        detteRepository.mettreAJour(dette); // Mise à jour de la dette dans la base de données
    }

    @Override
    public List<Dette> listerDettesNonSoldees(Client client) {
        return detteRepository.trouverDettesNonSoldeesParClient(client); // Récupère les dettes non soldées via le repository
    }

        @Override
        public List<Dette> listerDettesSoldees() {
            return detteRepository.listerTous().stream()
                    .filter(dette -> dette.getMontantRestant() == 0)
                    .collect(Collectors.toList());
        }

    @Override
    public List<Dette> listerDettesEnCours(String etat) {
        return detteRepository.listerTous().stream()
                .filter(dette -> "EnCours".equals(etat) && dette.getMontantRestant() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public void archiverDettes(List<Dette> dettes) {
        for (Dette dette : dettes) {
            dette.setArchive(true);
            detteRepository.mettreAJour(dette);
        }
    }


    @Override
    public List<Dette> listerDettesNonSoldeesPourClient(Client client) {
        return detteRepository.listerParClientEtEtat(client, false); // false = non soldée
    }

    @Override
    public boolean demanderDette(Client client, double montant) {
        return detteRepository.creerDemande(client, montant);
    }



    @Override
    public List<Dette> listerDemandesPourClient(Client client) {
        return detteRepository.listerDemandesParClient(client);
    }

        @Override
        public boolean relancerDemande(Client client, int demandeId) {
            Dette demande = detteRepository.trouverParId(demandeId);
            if (demande != null && demande.getEtat().equals("ANNULÉE")) {
                demande.setEtat("EN COURS");
                detteRepository.mettreAJour(demande);
                return true;
            }
            return false;
        }



}