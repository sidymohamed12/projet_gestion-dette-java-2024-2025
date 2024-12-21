package com.dette.views.viewspe;

import java.util.List;

import com.dette.core.View;
import com.dette.entities.Article;
import com.dette.entities.Dette;

public interface IArticleView extends View<Article> {
    void listerArticleDispo(List<Article> articles);

    Article updateQteArticle();

    void listerArticleDette(Dette dette);
}
