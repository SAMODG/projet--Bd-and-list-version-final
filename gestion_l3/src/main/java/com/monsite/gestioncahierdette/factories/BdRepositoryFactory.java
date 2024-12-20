package com.monsite.gestioncahierdette.factories;

import com.monsite.gestioncahierdette.repositories.bd.*;
import com.monsite.gestioncahierdette.repositories.ripo.*;

public class BdRepositoryFactory implements RepositoryFactory {

    @Override
    public ArticleRepository getArticleRepository() {
        return new ArticleRepositoryBd();
    }

    @Override
    public ClientRepository getClientRepository() {
        return new ClientRepositoryBd();
    }

    @Override
    public DetteRepository getDetteRepository() {
        return new DetteRepositoryBd();
    }

    @Override
    public PaiementRepository getPaiementRepository() {
        return new PaiementRepositoryBd();
    }

    @Override
    public UserRepository getUserRepository() {
        return new UserRepositoryBd();
    }
}
