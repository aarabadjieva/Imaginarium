package project.imaginarium.web.view.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.web.api.models.article.ArticleResponseModel;

@Controller
@AllArgsConstructor
@RequestMapping("/articles")
public class ArticlesController {

    public final static String ARTICLES_CREATE_VIEW_NAME = "articles/create.html";
    public final static String ARTICLES_CONTENT_VIEW_NAME = "articles/content.html";

    private final ArticlesService articlesService;
    private final ModelMapper mapper;


    @GetMapping("/create")
    public String createArticle(){
        return ARTICLES_CREATE_VIEW_NAME;
    }


    @GetMapping("/{name}")
    public ModelAndView articleContent(ModelAndView modelAndView, @PathVariable String name){
       ArticleResponseModel article = mapper.map(articlesService.findByTitle(name), ArticleResponseModel.class);
       modelAndView.addObject("article", article);
       modelAndView.setViewName(ARTICLES_CONTENT_VIEW_NAME);
       return modelAndView;
    }

    @GetMapping("/edit/{title}")
    public ModelAndView getEditArticle(@PathVariable String title) {
        //TODO: edit article
        return new ModelAndView("redirect:/articles/" + title);
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
