package project.imaginarium.service.services.impl;

import project.imaginarium.data.repositories.ArticleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.imaginarium.data.models.Article;
import project.imaginarium.service.models.ArticleServiceModel;
import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.web.api.models.article.ArticleResponseModel;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticlesServiceImpl implements ArticlesService {

    private final ArticleRepository articleRepository;
    private final ModelMapper mapper;


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

    @Override
    public void editArticle(ArticleResponseModel model) {
        Article article = articleRepository.findByTitle(model.getTitle());
        article.setTitle(model.getTitle());
        article.setContent(model.getContent());
        article.setPicture(model.getPicture());
        article.setDate(model.getDate());
        articleRepository.saveAndFlush(article);
    }

}
