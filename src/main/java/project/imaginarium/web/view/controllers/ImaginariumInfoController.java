package project.imaginarium.web.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ImaginariumInfoController {

    @GetMapping("/")
    public String getHome(){
        return "imaginarium/home.html";
    }

    @GetMapping("about")
    public String getAboutInfo() {
        return "imaginarium/about.html";
    }

    @GetMapping("contacts")
    public String getContacts(){
        return "imaginarium/contacts.html";
    }

    @GetMapping("blog")
    public String getBlog(){
        return "imaginarium/blog.html";
    }

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleException(Throwable exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error-custom.html");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
}
