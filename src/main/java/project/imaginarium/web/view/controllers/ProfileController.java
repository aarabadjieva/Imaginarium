package project.imaginarium.web.view.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.data.models.users.User;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.web.api.models.article.ArticleResponseModel;
import project.imaginarium.web.api.models.user.response.GuideResponseModel;
import project.imaginarium.web.api.models.user.response.PartnerResponseModel;
import project.imaginarium.web.view.models.user.UserMainView;
import project.imaginarium.web.view.models.user.edit.ClientEditModel;
import project.imaginarium.web.view.models.user.edit.GuideEditModel;
import project.imaginarium.web.view.models.user.edit.PartnerEditModel;
import project.imaginarium.web.view.models.user.edit.UserChangePictureModel;
import project.imaginarium.web.view.models.user.view.ClientViewModel;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

    public final static String CLIENT_PROFILE_VIEW_NAME = "/users/profile/client.html";
    public final static String PARTNER_PROFILE_VIEW_NAME = "/users/profile/partner.html";
    public final static String GUIDE_PROFILE_VIEW_NAME = "/users/profile/guide.html";
    public final static String ADMIN_PROFILE_VIEW_NAME = "/users/profile/admin.html";
    public final static String CLIENT_EDIT_PROFILE_VIEW_NAME = "/users/profile/edit/client.html";
    public final static String PARTNER_EDIT_PROFILE_VIEW_NAME = "/users/profile/edit/partner.html";
    public final static String GUIDE_EDIT_PROFILE_VIEW_NAME = "/users/profile/edit/guide.html";

    private final ModelMapper mapper;
    private final UserService userService;
    private final ArticlesService articlesService;
    private final List<String> countries;

    @GetMapping("/{role}/{name}")
    public ModelAndView getUserProfile(ModelAndView modelAndView, @PathVariable String role, @PathVariable String name) {
        switch (role) {
            case "client":
                ClientViewModel client = mapper.map(userService.findClientByUsername(name), ClientViewModel.class);
                modelAndView.addObject("client", client);
                modelAndView.setViewName(CLIENT_PROFILE_VIEW_NAME);
                break;
            case "partner":
                PartnerResponseModel partner = mapper.map(userService.findPartnerByUsername(name), PartnerResponseModel.class);
                modelAndView.addObject("partner", partner);
                modelAndView.setViewName(PARTNER_PROFILE_VIEW_NAME);
                break;
            case "guide":
                GuideResponseModel guide = mapper.map(userService.findGuideByUsername(name), GuideResponseModel.class);
                modelAndView.addObject("guide", guide);
                modelAndView.setViewName(GUIDE_PROFILE_VIEW_NAME);
                break;
            case "admin":
                if (SecurityContextHolder.getContext().getAuthentication().getName().equals(name)) {
                    UserMainView admin = mapper.map(userService.findClientByUsername(name), UserMainView.class);
                    List<UserMainView> allUsers = userService.findAllUsers().stream()
                            .map(u -> mapper.map(u, UserMainView.class))
                            .collect(Collectors.toList());
                    List<ArticleResponseModel> allArticles = articlesService.findAllArticles().stream()
                            .map(a -> mapper.map(a, ArticleResponseModel.class))
                            .collect(Collectors.toList());
                    modelAndView.addObject("admin", admin);
                    modelAndView.addObject("users", allUsers);
                    modelAndView.addObject("articles", allArticles);
                    modelAndView.setViewName(ADMIN_PROFILE_VIEW_NAME);
                }else {
                    client = mapper.map(userService.findClientByUsername(name), ClientViewModel.class);
                    modelAndView.addObject("client", client);
                    modelAndView.setViewName(CLIENT_PROFILE_VIEW_NAME);
                }
                break;
        }
        return modelAndView;
    }

    @GetMapping("/edit/{role}/{name}")
    public ModelAndView editProfile(@PathVariable String name, @PathVariable String role, ModelAndView modelAndView) {
        switch (role) {
            case "client":
                modelAndView.setViewName(CLIENT_EDIT_PROFILE_VIEW_NAME);
                ClientViewModel client = mapper.map(userService.findClientByUsername(name), ClientViewModel.class);
                modelAndView.addObject("client", client);
                modelAndView.addObject("countries", countries);
                break;
            case "partner":
                modelAndView.setViewName(PARTNER_EDIT_PROFILE_VIEW_NAME);
                PartnerResponseModel partner = mapper.map(userService.findPartnerByUsername(name), PartnerResponseModel.class);
                modelAndView.addObject("partner", partner);
                break;
            case "guide":
                modelAndView.setViewName(GUIDE_EDIT_PROFILE_VIEW_NAME);
                GuideResponseModel guide = mapper.map(userService.findGuideByUsername(name), GuideResponseModel.class);
                modelAndView.addObject("guide", guide);
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
                    modelAndView.addObject("client",client);
                }catch (Exception e) {
                    modelAndView.setViewName("redirect:/profile/edit/client/" + name);
                }
                break;
            case "partner":
                partner.setUsername(name);
                try {
                    userService.updatePartner(partner);
                    modelAndView.setViewName("redirect:/profile/partner/" + name);
                } catch (Exception e) {
                    modelAndView.setViewName("redirect:/profile/edit/partner/" + name);
                }
                break;
            case "guide":
                guide.setUsername(name);
                try {
                    userService.updateGuide(guide);
                    modelAndView.setViewName("redirect:/profile/guide/" + name);
                    modelAndView.addObject("guide", guide);
                } catch (Exception e) {
                    modelAndView.setViewName("redirect:/profile/edit/guide/" + name);
                }
        }
        return modelAndView;
    }

    @PostMapping("/{username}/change_picture")
    public String changePicture(@PathVariable String username, @ModelAttribute UserChangePictureModel model) throws IOException {
        userService.changePicture(username, model.getPicture());
        return "redirect:/";
    }

    @GetMapping("/{user}/save/{offer}")
    public String saveOffer(@PathVariable String user,
                                  @PathVariable String offer){
        userService.clientAddOffer(user, offer);
        return "redirect:/profile/client/"+user;
    }

    @GetMapping("/{user}/delete/{offer}")
    public String deleteOffer(@PathVariable String user,
                            @PathVariable String offer){
        userService.clientDeleteOffer(user, offer);
        return "redirect:/profile/client/"+user;
    }

    @PostMapping("/create/admin/{name}")
    public String createAdmin(@PathVariable String name, Authentication authentication){
        userService.makeAdmin(name);
        User admin = (User) authentication.getPrincipal();
        return "redirect:/profile/admin/"+ admin.getUsername();
    }


    @PostMapping("/delete/admin/{name}")
    public String deleteAdmin(@PathVariable String name, Authentication authentication){
        userService.deleteAdmin(name);
        User admin = (User) authentication.getPrincipal();
        return "redirect:/profile/admin/"+ admin.getUsername();
    }

    @ExceptionHandler(NoSuchUser.class)
    public ModelAndView handleException(Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error-custom.html");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
