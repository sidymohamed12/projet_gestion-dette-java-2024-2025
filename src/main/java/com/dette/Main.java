package com.dette;

import java.util.Scanner;

import com.dette.controllers.AdminController;
import com.dette.controllers.BoutiquierController;
import com.dette.controllers.ClientController;
import com.dette.core.Controller;
import com.dette.core.factory.Impl.FactoryRepo;
import com.dette.core.factory.Impl.FactoryService;
import com.dette.core.factory.Impl.FactoryView;

import com.dette.entities.Article;
import com.dette.entities.Client;
import com.dette.entities.Detail;
import com.dette.entities.Dette;
import com.dette.entities.Payement;
import com.dette.entities.User;
import com.dette.entities.UserConnect;
import com.dette.repository.implement.ArticleRepository;
import com.dette.repository.implement.ClientRepository;
import com.dette.repository.implement.DetailRepository;
import com.dette.repository.implement.DetteRepository;
import com.dette.repository.implement.PayementRepository;
import com.dette.repository.implement.UserRepository;

import com.dette.services.servicespe.IClientService;
import com.dette.services.servicespe.IDetailService;
import com.dette.services.servicespe.IDetteService;
import com.dette.services.servicespe.IPayementService;
import com.dette.services.servicespe.IArticleService;
import com.dette.services.servicespe.IUserService;

import com.dette.services.ArticleService;
import com.dette.services.ClientService;
import com.dette.services.DetailService;
import com.dette.services.DetteService;
import com.dette.services.PayementService;
import com.dette.services.UserService;

import com.dette.views.ArticleView;
import com.dette.views.ClientView;
import com.dette.views.DetteView;
import com.dette.views.PayementView;
import com.dette.views.UserView;
import com.dette.views.viewspe.IArticleView;
import com.dette.views.viewspe.IClientView;
import com.dette.views.viewspe.IDetteView;
import com.dette.views.viewspe.IPayementView;
import com.dette.views.viewspe.IUserView;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // ----------------------------- FACTORIES -----------------------------

        // factory repository
        FactoryRepo<Client> clientRepoFactory = new FactoryRepo<>(Client.class);
        FactoryRepo<User> userRepoFactory = new FactoryRepo<>(User.class);
        FactoryRepo<Article> articleRepoFactory = new FactoryRepo<>(Article.class);
        FactoryRepo<Dette> detteRepoFactory = new FactoryRepo<>(Dette.class);
        FactoryRepo<Detail> detailRepoFactory = new FactoryRepo<>(Detail.class);
        FactoryRepo<Payement> payementRepoFactory = new FactoryRepo<>(Payement.class);

        // initialisation des SERVICE pour chaque entité
        ClientRepository clientRepository = (ClientRepository) clientRepoFactory.createRepository();
        UserRepository userRepository = (UserRepository) userRepoFactory.createRepository();
        ArticleRepository articleRepository = (ArticleRepository) articleRepoFactory.createRepository();
        DetteRepository detteRepository = (DetteRepository) detteRepoFactory.createRepository();
        DetailRepository detailRepository = (DetailRepository) detailRepoFactory.createRepository();
        PayementRepository payementRepository = (PayementRepository) payementRepoFactory.createRepository();

        // factory service
        FactoryService<Client> clientServiceFactory = new FactoryService<>(Client.class, clientRepository);
        FactoryService<User> userServiceFactory = new FactoryService<>(User.class, userRepository);
        FactoryService<Article> articleServiceFactory = new FactoryService<>(Article.class, articleRepository);
        FactoryService<Dette> detteServiceFactory = new FactoryService<>(Dette.class, detteRepository);
        FactoryService<Detail> detailServiceFactory = new FactoryService<>(Detail.class, detailRepository);
        FactoryService<Payement> payementServiceFactory = new FactoryService<>(Payement.class, payementRepository);

        // initialisation des SERVICE pour chaque entité
        IClientService clientService = (ClientService) clientServiceFactory.createService();
        IUserService userService = (UserService) userServiceFactory.createService();
        IArticleService articleService = (ArticleService) articleServiceFactory.createService();
        IDetteService detteService = (DetteService) detteServiceFactory.createService();
        IDetailService detailService = (DetailService) detailServiceFactory.createService();
        IPayementService payementService = (PayementService) payementServiceFactory.createService();

        // factory view
        FactoryView<User> userViewFactory = new FactoryView<>(User.class, userService, null, null, null, null, null,
                scanner);
        FactoryView<Client> clientViewFactory = new FactoryView<>(Client.class, userService, clientService, null, null,
                null,
                null, scanner);
        FactoryView<Article> articleViewFactory = new FactoryView<>(Article.class, null, null, articleService, null,
                null, null, scanner);
        FactoryView<Dette> detteViewFactory = new FactoryView<>(Dette.class, null, null, articleService, detteService,
                detailService, null, scanner);
        FactoryView<Payement> payementViewFactory = new FactoryView<>(Payement.class, null, null, null, detteService,
                null, payementService, scanner);

        // initialisation des VIEW pour chaque entité
        IUserView userView = (UserView) userViewFactory.createView();
        IClientView clientView = (ClientView) clientViewFactory.createView();
        IArticleView articleView = (ArticleView) articleViewFactory.createView();
        IDetteView detteView = (DetteView) detteViewFactory.createView();
        IPayementView payementView = (PayementView) payementViewFactory.createView();

        // -----------------------------RECUPERATION USER CONNECT --------------------

        User userConnected = userView.authentification();
        UserConnect.setUserConnecte(userConnected);
        // ---------------------- DEROULEMENT POUR CHHAQUE ROLE si le user exist
        // ----------------

        if (userConnected != null) {
            switch (userConnected.getRole()) {
                case admin -> {
                    System.out.println("Connecting to admin, WELCOME...");
                    Controller adminController = new AdminController(scanner, clientView, userView, userService,
                            articleService, articleView);
                    adminController.execute();
                }
                case boutiquier -> {
                    System.out.println("Connecting to boutiquier...");
                    Controller bouController = new BoutiquierController(scanner, clientView, clientService, detteView,
                            articleView, payementView, payementService, detteService);
                    bouController.execute();
                }
                case client -> {
                    System.out.println("Connecting to Client...");
                    Controller clientController = new ClientController(scanner, detteView, detteService, articleView,
                            payementView, clientService);
                    clientController.execute();
                }
                default -> {
                    System.out.println("role not found");
                }
            }
        }
    }

    public static int showClientMenu() {
        System.out.println("1-  Lister ses dettes non soldées avec l'option de voir les articles ou les paiements");
        System.out.println("2-  Faire une demande de Dette");
        System.out.println("3-  Filtrer demandes de dette par état (En Cours, ou Annuler)");
        System.out.println("4-  Envoyer une relance pour une demande de dette annuler");
        System.out.println("5- Quitter");
        return scanner.nextInt();
    }

}
