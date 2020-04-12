package project.imaginarium.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.service.services.RoleService;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.web.models.articles.ArticleViewModel;
import project.imaginarium.web.models.user.view.GuideViewModel;
import project.imaginarium.web.models.user.view.PartnerViewModel;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class ImaginariumInfoController {

    private final UserService userService;
    private final RoleService roleService;
    private final ArticlesService articlesService;
    private final ModelMapper mapper;


    public ImaginariumInfoController(UserService userService, RoleService roleService, ArticlesService articlesService, ModelMapper mapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.articlesService = articlesService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public String getHome(){
        return "imaginarium/home.html";
    }

    @GetMapping("about")
    public ModelAndView getAboutInfo(ModelAndView modelAndView) {
        if (roleService.allRoles().isEmpty()){
            roleService.seedRolesInDB();
        }
        List<PartnerViewModel> allPartners = userService.partners();
        List<GuideViewModel> allGuides = userService.guides();
        modelAndView.setViewName("imaginarium/about.html");
        modelAndView.addObject("partners", allPartners);
        modelAndView.addObject("guides", allGuides);
        return modelAndView;
    }

    @GetMapping("contacts")
    public String getContacts(){
        return "imaginarium/contacts.html";
    }

    @GetMapping("blog")
    public ModelAndView getBlog(ModelAndView modelAndView, HttpSession session){
        List<ArticleViewModel> allArticles = articlesService.findAllArticles()
                .stream()
                .map(a-> mapper.map(a, ArticleViewModel.class))
                .collect(Collectors.toList());
        modelAndView.setViewName("imaginarium/blog.html");
        modelAndView.addObject("articles", allArticles);
        return modelAndView;
    }

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleException(Throwable exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error-custom.html");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
}
