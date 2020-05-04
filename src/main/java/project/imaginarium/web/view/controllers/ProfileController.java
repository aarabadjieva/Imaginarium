package project.imaginarium.web.view.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.web.api.models.article.ArticleResponseModel;
import project.imaginarium.web.view.models.user.UserMainView;
import project.imaginarium.web.view.models.user.edit.ClientEditModel;
import project.imaginarium.web.view.models.user.edit.GuideEditModel;
import project.imaginarium.web.view.models.user.edit.PartnerEditModel;
import project.imaginarium.web.view.models.user.view.ClientViewModel;
import project.imaginarium.web.api.models.user.response.GuideResponseModel;
import project.imaginarium.web.api.models.user.response.PartnerResponseModel;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ModelMapper mapper;
    private final UserService userService;
    private final ArticlesService articlesService;
    private final List<String> countries;

    public ProfileController(ModelMapper mapper, UserService userService, ArticlesService articlesService, List<String> countries) {
        this.mapper = mapper;
        this.userService = userService;
        this.articlesService = articlesService;
        this.countries = countries;
    }

    @GetMapping("/{role}/{name}")
    public ModelAndView getUserProfile(ModelAndView modelAndView, @PathVariable String role, @PathVariable String name) {
        modelAndView.setViewName("users/profile/" + role + ".html");
        switch (role) {
            case "client":
                ClientViewModel client = mapper.map(userService.findClientByUsername(name), ClientViewModel.class);
                modelAndView.addObject("client", client);
                break;
            case "partner":
                PartnerResponseModel partner = mapper.map(userService.findPartnerByUsername(name), PartnerResponseModel.class);
                modelAndView.addObject("partner", partner);
                break;
            case "guide":
                GuideResponseModel guide = mapper.map(userService.findGuideByUsername(name), GuideResponseModel.class);
                modelAndView.addObject("guide", guide);
                break;
            case "admin":
                List<UserMainView> allUsers = userService.findAllUsers().stream()
                        .map(u->mapper.map(u, UserMainView.class))
                        .collect(Collectors.toList());
                List<ArticleResponseModel> allArticles = articlesService.findAllArticles().stream()
                        .map(a->mapper.map(a, ArticleResponseModel.class))
                        .collect(Collectors.toList());
                modelAndView.addObject("users", allUsers);
                modelAndView.addObject("articles", allArticles);
                break;
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/edit/{role}/{name}")
    public ModelAndView editProfile(@PathVariable String name, @PathVariable String role, ModelAndView modelAndView) {
        switch (role) {
            case "client":
                modelAndView.setViewName("/users/profile/edit/client.html");
                ClientViewModel client = mapper.map(userService.findClientByUsername(name), ClientViewModel.class);
                modelAndView.addObject("client", client);
                modelAndView.addObject("countries", countries);
                break;
            case "partner":
                modelAndView.setViewName("/users/profile/edit/partner.html");
                PartnerResponseModel partner = mapper.map(userService.findPartnerByUsername(name), PartnerResponseModel.class);
                modelAndView.addObject("partner", partner);
                break;
            case "guide":
                modelAndView.setViewName("/users/profile/edit/guide.html");
                GuideResponseModel guide = mapper.map(userService.findGuideByUsername(name), GuideResponseModel.class);
                modelAndView.addObject("guide", guide);
                break;
            case "admin":
                break;
        }
        return modelAndView;
    }

    @PostMapping("/edit/{role}/{name}")
    public ModelAndView updateProfile(@PathVariable String name, @PathVariable String role,
                                      ModelAndView modelAndView,
                                      @ModelAttribute ClientEditModel client,
                                      @ModelAttribute PartnerEditModel partner,
                                      @ModelAttribute GuideEditModel guide) {
        switch (role) {
            case "client":
                client.setUsername(name);
                try {
                    userService.updateClient(client);
                    modelAndView.setViewName("redirect:/profile/client/" + name);
                    modelAndView.addObject(client);
                } catch (Exception e) {
                    modelAndView.setViewName("redirect:/profile/edit/" + name);
                }
                break;
            case "partner":
                partner.setUsername(name);
                try {
                    userService.updatePartner(partner);
                    modelAndView.setViewName("redirect:/profile/partner/" + name);
                    modelAndView.addObject(partner);
                } catch (Exception e) {
                    modelAndView.setViewName("redirect:/profile/edit/" + name);
                }
                break;
            case "guide":
                guide.setUsername(name);
                try {
                    userService.updateGuide(guide);
                    modelAndView.setViewName("redirect:/profile/guide/" + name);
                    modelAndView.addObject(guide);
                } catch (Exception e) {
                    modelAndView.setViewName("redirect:/profile/edit/" + name);
                }
        }
        return modelAndView;
    }

    @GetMapping("/{user}/save/{offer}")
    public ModelAndView saveOffer(ModelAndView modelAndView, @PathVariable String user,
                                  @PathVariable String offer){
        userService.clientAddOffer(user, offer);
        modelAndView.setViewName("redirect:/profile/client/"+user);
        return modelAndView;
    }

    @PostMapping("/create/admin/{name}")
    public ModelAndView createAdmin(ModelAndView modelAndView, @PathVariable String name, HttpSession session){
        userService.makeAdmin(name);
        modelAndView.setViewName("redirect:/profile/admin/"+session.getAttribute("username"));
        return modelAndView;
    }

    @PostMapping("/delete/admin/{name}")
    public ModelAndView deleteAdmin(ModelAndView modelAndView, @PathVariable String name, HttpSession session){
        userService.deleteAdmin(name);
        modelAndView.setViewName("redirect:/profile/admin/"+session.getAttribute("username"));
        return modelAndView;
    }

    @ExceptionHandler(NoSuchUser.class)
    public ModelAndView handleException(Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error-custom.html");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
}
