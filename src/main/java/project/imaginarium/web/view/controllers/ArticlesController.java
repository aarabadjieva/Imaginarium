package project.imaginarium.web.view.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.project.imaginarium.services.ArticlesService;
import project.imaginarium.web.api.models.article.ArticleResponseModel;

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


    @GetMapping("/{name}")
    public ModelAndView articleContent(ModelAndView modelAndView, @PathVariable String name){
       ArticleResponseModel article = mapper.map(articlesService.findByTitle(name), ArticleResponseModel.class);
       modelAndView.addObject("article", article);
       modelAndView.setViewName("articles/content.html");
       return modelAndView;
    }

    @GetMapping("/edit/{title}")
    public ModelAndView getEditArticle(@PathVariable String title) {
        //TO DO: edit article
        return new ModelAndView("redirect:/articles/" + title);
    }
}
