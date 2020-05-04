package project.imaginarium.web.view.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.models.user.ClientRegisterServiceModel;
import project.imaginarium.service.models.user.PartnerRegisterServiceModel;
import project.imaginarium.service.models.user.UserLoggedServiceModel;
import project.imaginarium.service.models.user.UserServiceLoginModel;
import com.project.imaginarium.services.CloudinaryService;
import com.project.imaginarium.services.RoleService;
import com.project.imaginarium.services.user.UserService;
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

    private final ModelMapper mapper;
    private final UserService userService;
    private final RoleService roleService;
    private final List<String> countries;
    private final CloudinaryService cloudinaryService;

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
            session.setAttribute("user", serviceModel);
            session.setAttribute("username", serviceModel.getUsername());
            if (serviceModel.getAuthorities().contains(roleService.findRoleByName("ADMIN"))){
                session.setAttribute("role", "admin");
            }else {

                session.setAttribute("role", "client");
            }
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
            return new ModelAndView("redirect:/users/register/partner.html");
        }
    }

    @PostMapping("/login")
    public ModelAndView getLogin(@ModelAttribute UserLoginModel model, HttpSession session) throws Exception {
        UserServiceLoginModel serviceModel = mapper.map(model, UserServiceLoginModel.class);
            UserLoggedServiceModel user = userService.login(serviceModel);
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            switch (user.getSector()){
                case HOTELS:
                case VEHICLES:
                case TIME_TRAVEL:
                    session.setAttribute("role", "partner");
                    break;
                case GUIDES:
                    session.setAttribute("role", "guide");
                    break;
                default:
                    session.setAttribute("role", "client");
            }
            if (user.getAuthorities().contains(roleService.findRoleByName("ADMIN"))){
                session.setAttribute("role", "admin");
            }
            return new ModelAndView("redirect:/profile/" + session.getAttribute("role") + "/" + user.getUsername());

    }

    @ExceptionHandler(NoSuchUser.class)
    public ModelAndView handleException(Throwable exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error-custom.html");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
}
