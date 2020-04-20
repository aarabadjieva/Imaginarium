package project.imaginarium.service.services.impl;

import com.project.imaginarium.ImaginariumApplicationTests;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import project.imaginarium.data.models.Article;
import project.imaginarium.data.repositories.ArticleRepository;
import project.imaginarium.service.models.ArticleServiceModel;
import project.imaginarium.service.services.ArticlesService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ArticlesServiceImplTest extends ImaginariumApplicationTests {

    @MockBean
    private ArticleRepository articleRepository;

    @Autowired
    private ArticlesService service;

    @Test
    void findAllArticles_shouldReturnListOfAllArticles() {
        List<Article> articles = getDummyArticles(3);
        Mockito.when(articleRepository.findAll()).thenReturn(articles);
        List<ArticleServiceModel> allArticles = service.findAllArticles();
        assertEquals(articles.size(), allArticles.size());
        assertEquals(articles.get(0).getTitle(), allArticles.get(0).getTitle());
        assertEquals(articles.get(1).getTitle(), allArticles.get(1).getTitle());
        assertEquals(articles.get(2).getTitle(), allArticles.get(2).getTitle());
    }

    @Test
    void saveArticle_shouldSaveArticle() {
        ArticleServiceModel article = new ArticleServiceModel();
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
                    article.setDate("someDate");
                    article.setPicture("pic");
                    article.setContent("someContent");
                    return article;
                })
                .collect(Collectors.toList());
    }
}