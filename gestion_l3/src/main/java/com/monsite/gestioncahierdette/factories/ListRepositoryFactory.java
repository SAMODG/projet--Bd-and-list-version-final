package com.monsite.gestioncahierdette.factories;

import com.monsite.gestioncahierdette.repositories.impl.*;
import com.monsite.gestioncahierdette.repositories.ripo.*;

public class ListRepositoryFactory implements RepositoryFactory {

    @Override
    public ArticleRepository getArticleRepository() {
        return new ArticleRepositoryImpl();
    }

    @Override
    public ClientRepository getClientRepository() {
        return new ClientRepositoryImpl();
    }

    @Override
    public DetteRepository getDetteRepository() {
        return new DetteRepositoryImpl();
    }

    @Override
    public PaiementRepository getPaiementRepository() {
        return new PaiementRepositoryImpl();
    }

    @Override
    public UserRepository getUserRepository() {
        return new UserRepositoryImpl();
    }
}
