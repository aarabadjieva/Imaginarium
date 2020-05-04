package com.project.imaginarium.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.imaginarium.data.models.Article;
import project.imaginarium.data.repositories.ArticleRepository;
import project.imaginarium.service.models.ArticleServiceModel;
import com.project.imaginarium.services.ArticlesService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticlesServiceImpl implements ArticlesService {

    private final ArticleRepository articleRepository;
    private final ModelMapper mapper;

    public ArticlesServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.mapper = modelMapper;
    }

    @Override
    public List<ArticleServiceModel> findAllArticles() {
        return articleRepository.findAll().stream()
                .map(a-> mapper.map(a, ArticleServiceModel.class))
                .sorted(Comparator.comparing(ArticleServiceModel::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void saveArticle(ArticleServiceModel model) {
        articleRepository.saveAndFlush(mapper.map(model, Article.class));
    }

    @Override
    public ArticleServiceModel findByTitle(String title) {
        return mapper.map(articleRepository.findByTitle(title), ArticleServiceModel.class);
    }

    @Override
    public void deleteArticle(String title) {
        Article article = articleRepository.findByTitle(title);
        articleRepository.delete(article);
    }

}
