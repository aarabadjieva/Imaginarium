package project.imaginarium.web.view.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.models.user.ClientRegisterServiceModel;
import project.imaginarium.service.models.user.PartnerRegisterServiceModel;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.web.view.models.user.register.ClientRegisterModel;
import project.imaginarium.web.view.models.user.register.PartnerRegisterModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping()
public class UsersController {

    public final static String USERS_REGISTER_CLIENT_VIEW_NAME = "/users/register/client.html";
    public final static String USERS_REGISTER_PARTNER_VIEW_NAME = "/users/register/partner.html";

    private final ModelMapper mapper;
    private final UserService userService;
    private final List<String> countries;

    @GetMapping("/register/user")
    public ModelAndView getClientRegister(ModelAndView modelAndView) {
        modelAndView.addObject("countries", countries);
        modelAndView.setViewName(USERS_REGISTER_CLIENT_VIEW_NAME);
        return modelAndView;
    }

    @PostMapping("/register/user")
    public ModelAndView createClient(@ModelAttribute ClientRegisterModel model, HttpServletRequest request) {
        ClientRegisterServiceModel serviceModel = mapper.map(model, ClientRegisterServiceModel.class);
        try {
            userService.saveClient(serviceModel);
            authWithHttpServletRequest(request, serviceModel.getUsername(), serviceModel.getPassword());
            return new ModelAndView("redirect:/profile/client/" + serviceModel.getUsername());
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView(USERS_REGISTER_CLIENT_VIEW_NAME);
            modelAndView.addObject("countries", countries);
            return modelAndView;
        }
    }

    @GetMapping("/register/partner")
    public String getPartnerRegister() {
        return USERS_REGISTER_PARTNER_VIEW_NAME;
    }

    @PostMapping("/register/partner")
    public ModelAndView createPartner(@ModelAttribute PartnerRegisterModel model, HttpServletRequest request) {
        PartnerRegisterServiceModel serviceModel = mapper.map(model, PartnerRegisterServiceModel.class);
        try {
            userService.savePartner(serviceModel);
            authWithHttpServletRequest(request, serviceModel.getUsername(), serviceModel.getPassword());
            return new ModelAndView("redirect:/profile/partner/" + serviceModel.getUsername());
        } catch (Exception e) {
            return new ModelAndView(USERS_REGISTER_PARTNER_VIEW_NAME);
        }
    }

    @ExceptionHandler(NoSuchUser.class)
    public ModelAndView handleException(Throwable exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error-custom.html");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            handleException(e);
        }
    }
}
