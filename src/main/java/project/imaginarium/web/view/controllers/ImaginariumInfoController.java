package project.imaginarium.web.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ImaginariumInfoController {

    public final static String IMAGINARIUM_HOME_VIEW_NAME = "imaginarium/home.html";
    public final static String IMAGINARIUM_ABOUT_VIEW_NAME = "imaginarium/about.html";
    public final static String IMAGINARIUM_BLOG_VIEW_NAME = "imaginarium/blog.html";
    public final static String IMAGINARIUM_DUMMY_VIEW_NAME = "imaginarium/dummy.html";

    @GetMapping("/")
    public String getHome(){
        return IMAGINARIUM_HOME_VIEW_NAME;
    }

    @GetMapping("about")
    public String getAboutInfo() {
        return IMAGINARIUM_ABOUT_VIEW_NAME;
    }

    @GetMapping("blog")
    public String getBlog(){
        return IMAGINARIUM_BLOG_VIEW_NAME;
    }

    @GetMapping("dummy")
    public String getDummy(){
        return IMAGINARIUM_DUMMY_VIEW_NAME;
    }

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleException(Throwable exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error-custom.html");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
}
