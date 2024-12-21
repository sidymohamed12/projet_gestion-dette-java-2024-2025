package com.dette.controllers;

import java.util.Scanner;

import com.dette.core.Controller;
import com.dette.entities.User;
import com.dette.services.servicespe.IArticleService;
import com.dette.services.servicespe.IUserService;
import com.dette.views.viewspe.IArticleView;
import com.dette.views.viewspe.IClientView;
import com.dette.views.viewspe.IUserView;

public class AdminController implements Controller {
    private Scanner scanner;
    private IClientView clientView;
    private IUserView userView;
    private IUserService userService;
    private IArticleService articleService;
    private IArticleView articleView;

    public AdminController(Scanner scanner,
            IClientView clientView,
            IUserView userView, IUserService userService,
            IArticleService articleService, IArticleView articleView) {
        this.scanner = scanner;
        this.clientView = clientView;
        this.userService = userService;
        this.userView = userView;
        this.articleService = articleService;
        this.articleView = articleView;
    }

    @Override
    public int menu() {
        System.out.println("1- Créer un compte utilisateur à un client n'ayant pas de compte");
        System.out.println("2- Créer un compte utilisateur avec un rôle Boutiquier ou  Admin");
        System.out.println("3- Activer un compte utilisateur");
        System.out.println("4- Désactiver un compte utilisateur");
        System.out.println("5- Afficher les comptes utilisateurs actifs");
        System.out.println("6- Afficher les comptes utilisateurs  par rôle");
        System.out.println("7- Ajouter un article");
        System.out.println("8- Lister tous les articles");
        System.out.println("9- Lister article disponible");
        System.out.println("10- Mettre à jour la quantité d'un article");
        System.out.println("12- Deconnexion");
        return scanner.nextInt();
    }

    @Override
    public void execute() {
        int choix;
        do {
            switch (choix = menu()) {
                case 1 -> {

                    scanner.nextLine();
                    clientView.createUserForClient();
                    break;
                }
                case 2 -> {

                    scanner.nextLine();
                    userService.create(userView.saisie());
                    break;
                }
                case 3 -> {
                    scanner.nextLine();
                    User user = userView.getBy();
                    userService.update(userView.setEtatUser(true, user));
                    break;
                }
                case 4 -> {
                    scanner.nextLine();
                    User user = userView.getBy();
                    userService.update(userView.setEtatUser(false, user));
                    break;
                }
                case 5 -> {
                    userView.listerUserActif(userService.findAll());
                }
                case 6 -> {
                    userView.listerUserbyRole(userService.findAll());
                    break;
                }
                case 7 -> {

                    scanner.nextLine();
                    articleService.create(articleView.saisie());
                    break;
                }
                case 8 -> {
                    articleService.findAll().forEach(System.out::println);
                    break;
                }
                case 9 -> {

                    scanner.nextLine();
                    articleView.listerArticleDispo(articleService.findAll());
                    break;
                }
                case 10 -> {
                    scanner.nextLine();
                    articleService.update(articleView.updateQteArticle());
                    break;
                }
                case 12 -> {
                    System.out.println("Deconnexion fait ");
                    break;
                }
            }
        } while (choix != 12);
    }

}
