package com.dette.core.factory.Impl;

import java.util.Scanner;

import com.dette.core.View;
import com.dette.core.factory.IFactoryView;
import com.dette.entities.Article;
import com.dette.entities.Client;
import com.dette.entities.Dette;
import com.dette.entities.Payement;
import com.dette.entities.User;
import com.dette.services.servicespe.IArticleService;
import com.dette.services.servicespe.IClientService;
import com.dette.services.servicespe.IDetailService;
import com.dette.services.servicespe.IDetteService;
import com.dette.services.servicespe.IPayementService;
import com.dette.services.servicespe.IUserService;
import com.dette.views.ArticleView;
import com.dette.views.ClientView;
import com.dette.views.DetteView;
import com.dette.views.PayementView;
import com.dette.views.UserView;

public class FactoryView<T> implements IFactoryView<T> {
    private final View<T> view;

    public FactoryView(Class<T> clazz, IUserService userService, IClientService clientService,
            IArticleService articleService, IDetteService detteService,
            IDetailService detailService,IPayementService payementService, Scanner scanner) {

        if (Client.class.isAssignableFrom(clazz)) {
            this.view = (View<T>) new ClientView(scanner, clientService, userService);
        } else if (User.class.isAssignableFrom(clazz)) {
            this.view = (View<T>) new UserView(scanner, userService);
        } else if (Article.class.isAssignableFrom(clazz)) {
            this.view = (View<T>) new ArticleView(scanner, articleService);
        } else if (Dette.class.isAssignableFrom(clazz)) {
            this.view = (View<T>) new DetteView(scanner, articleService, detailService, detteService);
        } else if (Payement.class.isAssignableFrom(clazz)) {
            this.view = (View<T>) new PayementView(scanner, payementService, detteService);
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " + clazz.getName());
        }
    }

    @Override
    public View<T> createView() {
        return view;
    }
}
