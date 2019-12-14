package project.imaginarium.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.web.models.user.view.GuideViewModel;
import project.imaginarium.web.models.user.view.PartnerViewModel;

import java.util.List;

@Controller
@RequestMapping("/")
public class ImaginariumInfoController {

    private final UserService userService;


    public ImaginariumInfoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getHome(){
        return "imaginarium/home.html";
    }

    @GetMapping("about")
    public ModelAndView getAboutInfo(ModelAndView modelAndView) {
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
    public String getBlog(){
        return "imaginarium/blog.html";
    }
}
