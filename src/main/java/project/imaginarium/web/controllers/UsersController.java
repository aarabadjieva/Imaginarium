package project.imaginarium.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.service.models.user.ClientRegisterServiceModel;
import project.imaginarium.service.models.user.PartnerRegisterServiceModel;
import project.imaginarium.service.models.user.UserLoggedServiceModel;
import project.imaginarium.service.models.user.UserServiceLoginModel;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.web.models.user.UserLoginModel;
import project.imaginarium.web.models.user.register.ClientRegisterModel;
import project.imaginarium.web.models.user.register.PartnerRegisterModel;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final ModelMapper mapper;
    private final UserService userService;
    private final List<String> countries;

    public UsersController(ModelMapper mapper, UserService userService, List<String> countries) {
        this.mapper = mapper;
        this.userService = userService;
        this.countries = countries;
    }

    @GetMapping("/register/user")
    public ModelAndView getClientRegister(ModelAndView modelAndView) {
        modelAndView.addObject("countries", countries);
        modelAndView.setViewName("users/register/client.html");
        return modelAndView;
    }

    @PostMapping("/register/user")
    public ModelAndView createClient(@ModelAttribute ClientRegisterModel model, HttpSession session) {
        ClientRegisterServiceModel serviceModel = mapper.map(model, ClientRegisterServiceModel.class);
        try {
            userService.saveClient(serviceModel);
            session.setAttribute("username", serviceModel.getUsername());
            session.setAttribute("role", serviceModel.getRole().toString().toLowerCase());
            return new ModelAndView("redirect:/");
        } catch (Exception e) {
            return new ModelAndView("users/register/client");
        }
    }

    @GetMapping("/register/partner")
    public ModelAndView getPartnerRegister(ModelAndView modelAndView) {
        modelAndView.setViewName("users/register/partner.html");
        return modelAndView;
    }

    @PostMapping("/register/partner")
    public ModelAndView createPartner(@ModelAttribute PartnerRegisterModel model, HttpSession session) {
        PartnerRegisterServiceModel serviceModel = mapper.map(model, PartnerRegisterServiceModel.class);
        try {
            userService.savePartner(serviceModel);
            session.setAttribute("username", serviceModel.getUsername());
            session.setAttribute("role", serviceModel.getRole().toString().toLowerCase());
            session.setAttribute("sector", serviceModel.getSector().toString().toLowerCase());
            session.setAttribute("pic", serviceModel.getLogo());
            return new ModelAndView("redirect:/");
        } catch (Exception e) {
            return new ModelAndView("redirect:/users/register/partner");
        }
    }

    @PostMapping("/login")
    public ModelAndView getLogin(@ModelAttribute UserLoginModel model, HttpSession session) {
        UserServiceLoginModel serviceModel = mapper.map(model, UserServiceLoginModel.class);
        try {
            UserLoggedServiceModel user = userService.login(serviceModel);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole().toString().toLowerCase());
            session.setAttribute("sector", user.getSector().toString().toLowerCase());
            session.setAttribute("pic", user.getLogo());

            return new ModelAndView("redirect:/profile/" + user.getRole().toString().toLowerCase() + "/" + user.getUsername());
        } catch (Exception e) {
            return new ModelAndView("redirect:/");
        }
    }

}
