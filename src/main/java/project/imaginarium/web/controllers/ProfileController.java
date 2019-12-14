package project.imaginarium.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.web.models.user.edit.ClientEditModel;
import project.imaginarium.web.models.user.edit.GuideEditModel;
import project.imaginarium.web.models.user.edit.PartnerEditModel;
import project.imaginarium.web.models.user.view.ClientViewModel;
import project.imaginarium.web.models.user.view.GuideViewModel;
import project.imaginarium.web.models.user.view.PartnerViewModel;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ModelMapper mapper;
    private final UserService userService;
    private final List<String> countries;

    public ProfileController(ModelMapper mapper, UserService userService, List<String> countries) {
        this.mapper = mapper;
        this.userService = userService;
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
                PartnerViewModel partner = mapper.map(userService.findPartnerByUsername(name), PartnerViewModel.class);
                modelAndView.addObject("partner", partner);
                break;
            case "guide":
                GuideViewModel guide = mapper.map(userService.findGuideByUsername(name), GuideViewModel.class);
                modelAndView.addObject("guide", guide);
                break;
            case "admin":
                break;
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/edit/{name}")
    public ModelAndView editProfile(@PathVariable String name, ModelAndView modelAndView, HttpSession session) {
        String role = session.getAttribute("role").toString();
        switch (role) {
            case "client":
                modelAndView.setViewName("/users/profile/edit/client.html");
                ClientViewModel client = mapper.map(userService.findClientByUsername(name), ClientViewModel.class);
                modelAndView.addObject("client", client);
                modelAndView.addObject("countries", countries);
                break;
            case "partner":
                modelAndView.setViewName("/users/profile/edit/partner.html");
                PartnerViewModel partner = mapper.map(userService.findPartnerByUsername(name), PartnerViewModel.class);
                modelAndView.addObject("partner", partner);
                break;
            case "guide":
                modelAndView.setViewName("/users/profile/edit/guide.html");
                GuideViewModel guide = mapper.map(userService.findGuideByUsername(name), GuideViewModel.class);
                modelAndView.addObject("guide", guide);
                break;
            case "admin":
                break;
        }
        return modelAndView;
    }

    @PostMapping("/edit/{name}")
    public ModelAndView updateProfile(@PathVariable String name, ModelAndView modelAndView,
                                      @ModelAttribute ClientEditModel client,
                                      @ModelAttribute PartnerEditModel partner,
                                      @ModelAttribute GuideEditModel guide,
                                      HttpSession session) {
        switch (session.getAttribute("role").toString()) {
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
}
