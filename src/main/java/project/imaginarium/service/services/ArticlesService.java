package project.imaginarium.service.services;

import project.imaginarium.service.models.ArticleServiceModel;
import project.imaginarium.web.api.models.article.ArticleResponseModel;

import java.util.List;

public interface ArticlesService {
    List<ArticleServiceModel> findAllArticles();

    void saveArticle(ArticleServiceModel model);

    ArticleServiceModel findByTitle(String title);

    void deleteArticle(String title);

    void editArticle(ArticleResponseModel model);
}
