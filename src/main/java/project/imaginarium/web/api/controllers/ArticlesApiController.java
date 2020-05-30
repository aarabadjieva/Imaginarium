package project.imaginarium.web.api.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import project.imaginarium.service.models.ArticleServiceModel;
import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.web.api.models.article.ArticleRequestCreateModel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/articles")
public class ArticlesApiController {

    private final ArticlesService articlesService;
    private final ModelMapper mapper;


    @PostMapping("/create")
    public void createArticle(@ModelAttribute ArticleRequestCreateModel model, HttpServletResponse response) throws IOException {
        ArticleServiceModel serviceModel = mapper.map(model, ArticleServiceModel.class);
        try {
            articlesService.saveArticle(serviceModel);
            response.sendRedirect("/blog");
        }catch (Exception e){
            response.sendRedirect("/articles/create");
        }
    }

    @PostMapping("/delete/{title}")
    public void deleteArticle(@PathVariable String title, HttpServletResponse response, Principal principal) throws IOException {
        articlesService.deleteArticle(title);
        response.sendRedirect("/profile/admin/" + principal.getName());
    }
}
