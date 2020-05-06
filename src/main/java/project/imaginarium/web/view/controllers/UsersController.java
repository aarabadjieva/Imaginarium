package project.imaginarium.web.view.controllers;

import org.springframework.http.HttpStatus;
import project.imaginarium.service.models.user.UserLoggedServiceModel;
import project.imaginarium.service.services.CloudinaryService;
import project.imaginarium.service.services.RoleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.models.user.ClientRegisterServiceModel;
import project.imaginarium.service.models.user.PartnerRegisterServiceModel;
import project.imaginarium.service.models.user.UserServiceLoginModel;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.web.view.models.user.UserLoginModel;
import project.imaginarium.web.view.models.user.register.ClientRegisterModel;
import project.imaginarium.web.view.models.user.register.PartnerRegisterModel;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UsersController {

    public final static String USERS_REGISTER_CLIENT_VIEW_NAME = "/users/register/client.html";
    public final static String USERS_REGISTER_PARTNER_VIEW_NAME = "/users/register/partner.html";

    private final ModelMapper mapper;
    private final UserService userService;
    private final RoleService roleService;
    private final List<String> countries;
    private final CloudinaryService cloudinaryService;

    @GetMapping("/register/user")
    public ModelAndView getClientRegister(ModelAndView modelAndView) {
        modelAndView.addObject("countries", countries);
        modelAndView.setViewName(USERS_REGISTER_CLIENT_VIEW_NAME);
        return modelAndView;
    }

    @PostMapping("/register/user")
    public ModelAndView createClient(@ModelAttribute ClientRegisterModel model, HttpSession session) {
        ClientRegisterServiceModel serviceModel = mapper.map(model, ClientRegisterServiceModel.class);
        try {
            userService.saveClient(serviceModel);
            session.setAttribute("user", serviceModel);
            session.setAttribute("username", serviceModel.getUsername());
            if (serviceModel.getAuthorities().contains(roleService.findRoleByName("ADMIN"))){
                session.setAttribute("role", "admin");
            }else {
                session.setAttribute("role", "client");
            }
            return new ModelAndView("redirect:/");
        } catch (Exception e) {
            return new ModelAndView(USERS_REGISTER_CLIENT_VIEW_NAME);
        }
    }

    @GetMapping("/register/partner")
    public String getPartnerRegister() {
        return USERS_REGISTER_PARTNER_VIEW_NAME;
    }

    @PostMapping("/register/partner")
    public ModelAndView createPartner(@ModelAttribute PartnerRegisterModel model, HttpSession session) throws IOException {
        PartnerRegisterServiceModel serviceModel = mapper.map(model, PartnerRegisterServiceModel.class);
        serviceModel.setLogo(cloudinaryService.upload(model.getLogo()));
        try {
            userService.savePartner(serviceModel);
            session.setAttribute("user", serviceModel);
            session.setAttribute("username", serviceModel.getUsername());
            if (serviceModel.getAuthorities().contains(roleService.findRoleByName("ADMIN"))){
                session.setAttribute("role", "admin");
            }else {

                session.setAttribute("role", "partner");
            }
            return new ModelAndView("redirect:/");
        } catch (Exception e) {
            return new ModelAndView(USERS_REGISTER_PARTNER_VIEW_NAME);
        }
    }

    @PostMapping("/login")
    public String getLogin(@ModelAttribute UserLoginModel model, HttpSession session) throws Exception {
        UserServiceLoginModel serviceModel = mapper.map(model, UserServiceLoginModel.class);
            UserLoggedServiceModel user = userService.login(serviceModel);
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            switch (user.getSector()){
                case HOTEL:
                case VEHICLE:
                case EVENT:
                    session.setAttribute("role", "partner");
                    break;
                case GUIDE:
                    session.setAttribute("role", "guide");
                    break;
                default:
                    session.setAttribute("role", "client");
            }
            if (user.getAuthorities().contains(roleService.findRoleByName("ADMIN"))){
                session.setAttribute("role", "admin");
            }
            return "redirect:/profile/" + session.getAttribute("role") + "/" + user.getUsername();

    }

    @ExceptionHandler(NoSuchUser.class)
    public ModelAndView handleException(Throwable exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error-custom.html");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
