package project.imaginarium.service.services;

import project.imaginarium.service.models.ArticleServiceModel;
import project.imaginarium.web.api.models.article.ArticleRequestCreateModel;
import project.imaginarium.web.api.models.article.ArticleResponseModel;

import java.io.IOException;
import java.util.List;

public interface ArticlesService {
    List<ArticleResponseModel> findAllArticles();

    void saveArticle(ArticleServiceModel model) throws IOException;

    ArticleServiceModel findByTitle(String title);

    void deleteArticle(String title);

    void editArticle(ArticleRequestCreateModel model, String title) throws IOException;
}
