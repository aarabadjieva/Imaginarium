package project.imaginarium.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.service.models.ArticleServiceModel;
import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.web.models.articles.ArticleCreateModel;
import project.imaginarium.web.models.articles.ArticleViewModel;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/articles")
public class ArticlesController {

    private final ArticlesService articlesService;
    private final ModelMapper mapper;

    public ArticlesController(ArticlesService articlesService, ModelMapper mapper) {
        this.articlesService = articlesService;
        this.mapper = mapper;
    }

    @GetMapping("/create")
    public ModelAndView createArticle(ModelAndView modelAndView){
        modelAndView.setViewName("articles/create.html");
        return modelAndView;
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
       ArticleViewModel article = mapper.map(articlesService.findByTitle(name), ArticleViewModel.class);
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
