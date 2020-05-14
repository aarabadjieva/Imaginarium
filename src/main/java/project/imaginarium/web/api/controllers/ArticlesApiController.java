package project.imaginarium.web.api.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.imaginarium.service.models.ArticleServiceModel;
import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.web.api.models.article.ArticleRequestCreateModel;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@AllArgsConstructor
public class ArticlesApiController {

    private final ArticlesService articlesService;
    private final ModelMapper mapper;


    @PostMapping("/articles/create")
    public void createArticle(ArticleRequestCreateModel model, HttpServletResponse response) throws IOException {
        ArticleServiceModel serviceModel = mapper.map(model, ArticleServiceModel.class);
        try {
            articlesService.saveArticle(serviceModel);
        }catch (Exception e){
            response.sendRedirect("/articles/create");
        }
        response.sendRedirect("/blog");
    }

    @PostMapping("articles/delete/{title}")
    public void deleteArticle(@PathVariable String title, HttpServletResponse response, HttpSession session) throws IOException {
        articlesService.deleteArticle(title);
        response.sendRedirect("/profile/admin/" + session.getAttribute("username"));
    }
}
