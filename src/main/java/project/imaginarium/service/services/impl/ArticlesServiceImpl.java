package project.imaginarium.service.services.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.imaginarium.data.models.Article;
import project.imaginarium.data.repositories.ArticleRepository;
import project.imaginarium.service.models.ArticleServiceModel;
import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.service.services.CloudinaryService;
import project.imaginarium.web.api.models.article.ArticleRequestCreateModel;
import project.imaginarium.web.api.models.article.ArticleResponseModel;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticlesServiceImpl implements ArticlesService {

    private final ArticleRepository articleRepository;
    private final ModelMapper mapper;
    private final CloudinaryService cloudinaryService;


    @Override
    public List<ArticleResponseModel> findAllArticles() {
        return articleRepository.findAll().stream()
                .map(a-> mapper.map(a, ArticleResponseModel.class))
                .sorted(Comparator.comparing(ArticleResponseModel::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void saveArticle(ArticleServiceModel model) throws IOException {
        Article article = mapper.map(model, Article.class);
        article.setPicture(cloudinaryService.upload(model.getPicture()));
        articleRepository.saveAndFlush(article);
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
    public void editArticle(ArticleRequestCreateModel model, String title) throws IOException {
        Article article = articleRepository.findByTitle(title);
        article.setTitle(model.getTitle());
        article.setContent(model.getContent());
        article.setPicture(cloudinaryService.upload(model.getPicture()));
        article.setDate(model.getDate());
        articleRepository.saveAndFlush(article);
    }

}
