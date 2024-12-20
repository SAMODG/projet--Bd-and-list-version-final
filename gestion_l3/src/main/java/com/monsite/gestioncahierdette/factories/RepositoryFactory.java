package com.monsite.gestioncahierdette.factories;

import com.monsite.gestioncahierdette.repositories.ripo.*;


public interface RepositoryFactory {
    ArticleRepository getArticleRepository();
    ClientRepository getClientRepository();
    DetteRepository getDetteRepository();
    PaiementRepository getPaiementRepository();
    UserRepository getUserRepository();
}

