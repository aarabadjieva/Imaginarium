package project.imaginarium.web.view.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.service.services.OffersService;
import project.imaginarium.web.view.models.offer.view.OfferShortViewModel;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class ImaginariumInfoController {

    public final static String IMAGINARIUM_HOME_VIEW_NAME = "imaginarium/home.html";
    public final static String IMAGINARIUM_ABOUT_VIEW_NAME = "imaginarium/about.html";
    public final static String IMAGINARIUM_BLOG_VIEW_NAME = "imaginarium/blog.html";
    public final static String IMAGINARIUM_DUMMY_VIEW_NAME = "imaginarium/dummy.html";
    private final OffersService offersService;
    private final ModelMapper mapper;

    @Autowired
    public ImaginariumInfoController(OffersService offersService, ModelMapper mapper) {
        this.offersService = offersService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ModelAndView getHome(ModelAndView modelAndView){
        List<OfferShortViewModel> offers =  offersService.findAllOffers()
                .stream()
                .map(o -> mapper.map(o, OfferShortViewModel.class))
                .collect(Collectors.toList());
        modelAndView.setViewName(IMAGINARIUM_HOME_VIEW_NAME);
        modelAndView.addObject("offers", offers);
        return modelAndView;
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
