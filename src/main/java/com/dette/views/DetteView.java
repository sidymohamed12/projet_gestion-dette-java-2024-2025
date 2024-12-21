package com.dette.views;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.dette.core.ViewImplement;
import com.dette.entities.Article;
import com.dette.entities.Client;
import com.dette.entities.Detail;
import com.dette.entities.Dette;
import com.dette.entities.UserConnect;
import com.dette.enums.Etat;
import com.dette.enums.Role;
import com.dette.services.servicespe.IArticleService;
import com.dette.services.servicespe.IDetailService;
import com.dette.services.servicespe.IDetteService;
import com.dette.views.viewspe.IDetteView;

public class DetteView extends ViewImplement<Dette> implements IDetteView {

    private IArticleService articleService;
    private IDetailService detailService;
    private IDetteService detteService;

    public DetteView(Scanner scanner, IArticleService articleService, IDetailService detailService,
            IDetteService detteService) {
        super(scanner);
        this.articleService = articleService;
        this.detailService = detailService;
        this.detteService = detteService;
    }

    @Override
    public Dette saisie() {
        Dette dette = new Dette();
        dette.setDate(LocalDateTime.now());
        if (UserConnect.getUserConnecte().getRole() == Role.boutiquier) {
            dette.setEtatD(Etat.accepter);
        } else {
            dette.setEtatD(Etat.encours);
        }
        dette.setMontantVerser(0.0);
        dette.setMontantRestant(dette.getMontant());
        dette.setMontant(0.0);
        dette.setArchiver(false);
        return dette;
    }

    @Override
    public void createDetteClient(Dette dette) {
        do {
            articleService.findAll()
                    .stream()
                    .filter(art -> art.getQteStock() != 0)
                    .forEach(System.out::println);

            System.out.println("Choisissez l'article par son ref : ");
            Article article = articleService.getBy(scanner.nextLine());
            System.out.println(article);

            if (article == null || article.getQteStock() == 0) {
                System.out.println("Article inexistant ou en rupture de stock. Veuillez recommencer.");
            } else {
                int qte;
                do {
                    System.out.println("Choisissez la quantité de l'article");
                    qte = scanner.nextInt();
                } while (qte > article.getQteStock() || qte <= 0);

                double montantArticle = qte * article.getPrix();
                article.setQteStock(article.getQteStock() - qte);
                articleService.update(article);
                Optional<Detail> existingDetail = dette.getDetails().stream()
                        .filter(detail -> detail.getArticle().equals(article))
                        .findFirst();

                if (existingDetail.isPresent()) {

                    Detail detail = existingDetail.get();
                    int nouvelleQuantite = detail.getQteVendu() + qte;
                    double nouveauMontantVendu = detail.getMontantVendu() + montantArticle;

                    detail.setQteVendu(nouvelleQuantite);
                    detail.setMontantVendu(nouveauMontantVendu);
                    detailService.update(detail);
                } else {

                    Detail detail = new Detail();
                    detail.setQteVendu(qte);
                    detail.setMontantVendu(montantArticle);
                    detail.setArticle(article);
                    detail.setDette(dette);
                    detailService.create(detail);
                    dette.addDetail(detail);

                }

                dette.setMontant(dette.getMontant() + montantArticle);

                System.out.println("Voulez-vous continuer ? ");
            }
        } while (askToContinue());

        detteService.update(dette);
    }

    @Override
    public Dette getBy() {
        // detteService.findAll().forEach(System.out::println);
        // System.out.println("Entrez le telephone du client : ");
        // String tel = scanner.nextLine();
        // Dette dette = detteService.getBy(tel);
        // return dette;
        return null;
    }

    @Override
    public Etat saisiEtat() {
        int choix;
        do {
            System.out.println("veuillez selectionner l'etat");
            for (Etat etat : Etat.values()) {
                System.out.println(etat.getId() + "-" + etat.name());
            }
            choix = scanner.nextInt();
        } while (choix <= 0 || choix > Role.values().length);

        return Etat.getEtatById(choix);
    }

    @Override
    public void listerDetteNonSolde(List<Dette> dettes) {
        dettes
                .stream()
                .filter(dette -> dette.getMontant() != dette.getMontantVerser())
                .forEach(System.out::println);
    }

    @Override
    public void listerDetteSolde(List<Dette> dettes) {
        dettes
                .stream()
                .filter(dette -> dette.getMontant() == dette.getMontantVerser())
                .forEach(System.out::println);
    }

    @Override
    public void filtrerDetteByEtat(Etat etat, List<Dette> dettes) {
        dettes
                .stream()
                .filter(dette -> dette.getEtatD().equals(etat))
                .forEach(System.out::println);
    }

    @Override
    public Dette getById() {
        System.out.println("Entrez l'id de la dette : ");
        int id = scanner.nextInt();
        Dette dette = detteService.getById(id);
        if (dette != null) {
            return dette;
        } else {
            System.out.println("No dette found");
            return null;
        }
    }

    @Override
    public Dette traiterDette(Dette dette) {
        if (dette.getEtatD().equals(Etat.encours)) {
            int choix;
            do {
                System.out.println("1- Accepter");
                System.out.println("2- Annuler");
                choix = scanner.nextInt();
            } while (choix <= 0 || choix > 2);

            switch (choix) {
                case 1:
                    dette.setEtatD(Etat.accepter);
                case 2:
                    dette.setEtatD(Etat.annuler);
                default:
                    break;
            }
        } else {
            System.out.println("🚨 cette dette est déjà acceptée ou annulée 🚨");
        }
        return dette;
    }

    @Override
    public void ListedetteOfClient(Client client, List<Dette> dettes) {
        dettes
                .stream()
                .filter(dette -> dette.getClientD().equals(client) && dette.getEtatD().equals(Etat.accepter)
                        && !dette.getMontantRestant().equals(0.0))
                .forEach(System.out::println);
    }

    @Override
    public void ListeDemandeDetteClient(Client client, List<Dette> dettes) {
        dettes
                .stream()
                .filter(dette -> dette.getClientD().equals(client) && !dette.getEtatD().equals(Etat.accepter))
                .forEach(System.out::println);
        int choix;
        do {
            System.out.println("1- encours");
            System.out.println("2- annuler");
            choix = scanner.nextInt();
        } while (choix <= 0 || choix > 2);
        Etat etat = Etat.getEtatById(choix);
        detteService.detteOfClient(client)
                .stream()
                .filter(dette -> dette.getClientD().equals(client) && dette.getEtatD().equals(etat))
                .forEach(System.out::println);

    }

}
