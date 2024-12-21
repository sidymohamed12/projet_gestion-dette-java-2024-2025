package com.dette.controllers;

import java.util.Scanner;

import com.dette.core.Controller;
import com.dette.entities.Client;
import com.dette.entities.Dette;
import com.dette.entities.UserConnect;
import com.dette.enums.Etat;
import com.dette.services.servicespe.IClientService;
import com.dette.services.servicespe.IDetteService;
import com.dette.views.viewspe.IArticleView;
import com.dette.views.viewspe.IDetteView;
import com.dette.views.viewspe.IPayementView;

public class ClientController implements Controller {
    private Scanner scanner;
    private IDetteView detteView;
    private IDetteService detteService;
    private IArticleView articleView;
    private IPayementView payementView;
    private IClientService clientService;
    private Client client;

    public ClientController(Scanner scanner, IDetteView detteView,
            IDetteService detteService, IArticleView articleView,
            IPayementView payementView, IClientService clientService) {
        this.scanner = scanner;
        this.detteService = detteService;
        this.detteView = detteView;
        this.articleView = articleView;
        this.payementView = payementView;
        this.clientService = clientService;
        this.client = this.clientService.getClientByUser(UserConnect.getUserConnecte());
    }

    @Override
    public int menu() {
        System.out.println("1-  Lister ses dettes non soldées avec l'option de voir les articles ou les paiements");
        System.out.println("2-  Faire une demande de Dette");
        System.out.println("3-  Filtrer demandes de dette par état(En Cours, ou Annuler)");
        System.out.println("4-  Envoyer une relance pour une demande de dette annuler");
        System.out.println("5- Quitter");
        return scanner.nextInt();
    }

    @Override
    public void execute() {
        int choix;
        do {
            switch (choix = menu()) {
                case 1 -> {
                    detteView.ListedetteOfClient(client, detteService.findAll());
                    System.out.println("voir détail d'une dette ?");
                    if (detteView.askToContinue()) {
                        Dette dette = detteView.getById();
                        if (dette != null && dette.getClientD().getId().equals(client.getId())
                                && !dette.getMontantRestant().equals(0.0) &&
                                dette.getEtatD().equals(Etat.accepter)) {
                            System.out.println("-------------- ARTICLES -------------");
                            articleView.listerArticleDette(dette);
                            System.out.println("-------------- PAYEMENTS ------------");
                            payementView.listePayementsDette(dette);
                        } else {
                            System.out.println("Aucune dette non-soldé disponible pour vous avec ce id.");
                        }
                    }
                }
                case 2 -> {
                    scanner.nextLine();
                    System.out.println(client);
                    Dette dette = detteView.saisie();
                    dette.setClientD(client);
                    client.addDettes(dette);
                    detteService.create(dette);
                    detteView.createDetteClient(dette);
                }
                case 3 -> {
                    detteView.ListeDemandeDetteClient(client, detteService.findAll());
                }
                case 4 -> {

                }
                case 5 -> {
                    System.out.println("deconnexion done !");
                }
            }
        } while (choix != 5);
    }

}
