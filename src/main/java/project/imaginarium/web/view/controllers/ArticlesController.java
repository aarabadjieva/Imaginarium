package project.imaginarium.web.view.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.service.models.ArticleServiceModel;
import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.web.view.models.article.ArticleCreateModel;
import project.imaginarium.web.api.models.article.ArticleResponseModel;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/articles")
public class ArticlesController {

    private final ArticlesService articlesService;
    private final ModelMapper mapper;


    @GetMapping("/create")
    public String createArticle(){
        return "articles/create.html";
    }

    @PostMapping("/create")
    public ModelAndView allArticles(ModelAndView modelAndView, @ModelAttribute ArticleCreateModel model){
        ArticleServiceModel serviceModel = mapper.map(model, ArticleServiceModel.class);
        try {
            articlesService.saveArticle(serviceModel);
        }catch (Exception e){
            modelAndView.setViewName("articles/create.html");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/blog");
        return modelAndView;
    }

    @GetMapping("/{name}")
    public ModelAndView articleContent(ModelAndView modelAndView, @PathVariable String name){
       ArticleResponseModel article = mapper.map(articlesService.findByTitle(name), ArticleResponseModel.class);
       modelAndView.addObject("article", article);
       modelAndView.setViewName("articles/content.html");
       return modelAndView;
    }

    @PostMapping("/delete/{title}")
    public ModelAndView deleteArticle(@PathVariable String title, HttpSession session) {
        articlesService.deleteArticle(title);
        return new ModelAndView("redirect:/profile/admin/" + session.getAttribute("username"));
    }

    @GetMapping("/edit/{title}")
    public ModelAndView getEditArticle(@PathVariable String title) {
        //TO DO: edit article
        return new ModelAndView("redirect:/articles/" + title);
    }
}
