package project.imaginarium.service.services.impl;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import project.imaginarium.base.ImaginariumApplicationBaseTests;
import project.imaginarium.data.models.Article;
import project.imaginarium.data.repositories.ArticleRepository;
import project.imaginarium.service.models.ArticleServiceModel;
import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.service.services.CloudinaryService;
import project.imaginarium.web.api.models.article.ArticleResponseModel;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ArticlesServiceTest extends ImaginariumApplicationBaseTests {

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private CloudinaryService cloudinaryService;

    @Autowired
    private ArticlesService service;

    @Test
    void findAllArticles_shouldReturnListOfAllArticlesOrderedByDate() {
        List<Article> articles = getDummyArticles(3);
        articles.get(1).setDate("2021-05-24");
        articles.get(2).setDate("2019-05-24");
        Mockito.when(articleRepository.findAll()).thenReturn(articles);
        List<ArticleResponseModel> allArticles = service.findAllArticles();
        assertEquals(articles.size(), allArticles.size());
        assertEquals(articles.get(1).getTitle(), allArticles.get(0).getTitle());
        assertEquals(articles.get(0).getTitle(), allArticles.get(1).getTitle());
        assertEquals(articles.get(2).getTitle(), allArticles.get(2).getTitle());
    }

    @Test
    void saveArticle_shouldSaveArticle() throws IOException {
        ArticleServiceModel article = new ArticleServiceModel();
        Mockito.when(cloudinaryService.upload(article.getPicture())).thenReturn("pictureURL");
        service.saveArticle(article);
        ArgumentCaptor<Article> captor = ArgumentCaptor.forClass(Article.class);
        Mockito.verify(articleRepository).saveAndFlush(captor.capture());
        Article entity = captor.getValue();
        assertNotNull(entity);
        assertEquals(article.getTitle(), entity.getTitle());
    }

    @Test
    void findArticleByTitle_shouldReturnArticleIfExist() {
        Article article = new Article();
        article.setTitle("Title");
        Mockito.when(articleRepository.findByTitle(article.getTitle())).thenReturn(article);
        ArticleServiceModel foundArticle = service.findByTitle(article.getTitle());
        assertEquals(article.getTitle(),foundArticle.getTitle());
    }

    @Test
    void deleteArticle_shouldDeleteArticle() {
        Article article = new Article();
        article.setTitle("Title");
        Mockito.when(articleRepository.findByTitle(article.getTitle())).thenReturn(article);
        service.deleteArticle(article.getTitle());
        Mockito.verify(articleRepository).delete(article);
    }

    private List<Article> getDummyArticles(int count) {
        return IntStream.range(0, count)
                .map(x -> x + 1)
                .mapToObj(id -> {
                    Article article = new Article();
                    article.setId(String.valueOf(id));
                    article.setTitle("Article " + id);
                    article.setDate("2020-05-02");
                    article.setPicture("pic");
                    article.setContent("someContent");
                    return article;
                })
                .collect(Collectors.toList());
    }
}