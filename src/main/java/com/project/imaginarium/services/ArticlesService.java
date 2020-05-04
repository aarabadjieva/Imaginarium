package com.project.imaginarium.services;

import project.imaginarium.service.models.ArticleServiceModel;

import java.util.List;

public interface ArticlesService {
    List<ArticleServiceModel> findAllArticles();

    void saveArticle(ArticleServiceModel model);

    ArticleServiceModel findByTitle(String title);

    void deleteArticle(String title);
}
