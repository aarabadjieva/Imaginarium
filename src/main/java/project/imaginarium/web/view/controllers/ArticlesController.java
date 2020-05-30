package project.imaginarium.web.view.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.web.api.models.article.ArticleRequestCreateModel;
import project.imaginarium.web.api.models.article.ArticleResponseModel;

@Controller
@AllArgsConstructor
@RequestMapping("/articles")
public class ArticlesController {

    public final static String ARTICLES_CREATE_VIEW_NAME = "/articles/create.html";
    public final static String ARTICLES_EDIT_VIEW_NAME = "/articles/edit.html";

    private final ArticlesService articlesService;
    private final ModelMapper mapper;


    @GetMapping("/create")
    public String createArticle(){
        return ARTICLES_CREATE_VIEW_NAME;
    }

    @GetMapping("/edit/{title}")
    public ModelAndView getEditArticle(@PathVariable String title) {
        ArticleResponseModel article = mapper.map(articlesService.findByTitle(title), ArticleResponseModel.class);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("article", article);
        modelAndView.setViewName(ARTICLES_EDIT_VIEW_NAME);
        return modelAndView;
    }

    @PostMapping("/edit/{title}")
    public ModelAndView getEditArticle(@ModelAttribute ArticleRequestCreateModel model, @PathVariable String title) {
        try{
            articlesService.editArticle(model, title);
        }catch (Exception ignored){

        }
        return new ModelAndView("redirect:/blog");
    }

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleException(Throwable exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error-custom.html");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
