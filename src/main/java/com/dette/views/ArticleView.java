package com.dette.views;

import java.util.List;
import java.util.Scanner;

import com.dette.core.ViewImplement;
import com.dette.entities.Article;
import com.dette.entities.Dette;
import com.dette.services.servicespe.IArticleService;
import com.dette.views.viewspe.IArticleView;

public class ArticleView extends ViewImplement<Article> implements IArticleView {

    private IArticleService articleService;

    public ArticleView(Scanner scanner, IArticleService articleService) {
        super(scanner);
        this.articleService = articleService;
    }

    @Override
    public Article saisie() {

        Article article = new Article();

        System.out.println("Entrez le libelle de l'article : ");
        article.setLibelle(scanner.nextLine());

        System.out.println("Entrez la quantité en stock de l'article : ");
        article.setQteStock(scanner.nextInt());

        System.out.println("Entrez le prix unitaire de l'article : ");
        article.setPrix(scanner.nextDouble());

        int id = articleService.count() + 1;
        int size = String.valueOf(id).length();
        article.setRef("ART" + "0".repeat(size > 4 ? 0 : 4 - size) + id);

        return article;
    }

    @Override
    public Article getBy() {
        articleService.findAll().forEach(System.out::println);
        System.out.println("Entrez la reference de l'article à mise à jour : ");
        String ref = scanner.nextLine();
        Article article = articleService.getBy(ref);
        return article;
    }

    @Override
    public void listerArticleDispo(List<Article> articles) {
        articles
                .stream()
                .filter(art -> art.getQteStock() != 0)
                .forEach(System.out::println);
    }

    @Override
    public Article updateQteArticle() {
        Article article = getBy();
        int newQte;
        if (article != null) {
            do {
                System.out.println(article);
                System.out.println("Entrez la quantité à ajouter : ");
                newQte = scanner.nextInt();
            } while (newQte <= 0);
            article.setQteStock(article.getQteStock() + newQte);
            return article;
        } else {
            System.out.println("🚨 Article introuvable 🚨");
            return null;
        }

    }

    @Override
    public void listerArticleDette(Dette dette) {
        articleService.getArticlesDette(dette).forEach(System.out::println);
    }
}
