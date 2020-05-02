package project.imaginarium.service.services.user.impl;

import org.springframework.stereotype.Service;
import project.imaginarium.data.models.users.Client;
import project.imaginarium.data.models.users.Guide;
import project.imaginarium.data.models.users.Partner;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.service.models.user.ClientRegisterServiceModel;
import project.imaginarium.service.models.user.PartnerRegisterServiceModel;
import project.imaginarium.service.services.user.UserValidationService;
import project.imaginarium.web.view.models.user.edit.ClientEditModel;
import project.imaginarium.web.view.models.user.edit.GuideEditModel;
import project.imaginarium.web.view.models.user.edit.PartnerEditModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserValidationServiceImpl implements UserValidationService {

    private final UserRepository userRepository;

    public UserValidationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValidUser(String password, String confirmPassword, String email, String username) {
        return isPasswordValid(password, confirmPassword) &&
                isEmailValid(email) &&
                !isUsernameTaken(username);
    }

    @Override
    public boolean isValidClient(ClientRegisterServiceModel model) {
        return isValidUser(model.getPassword(), model.getConfirmPassword(), model.getEmail(), model.getUsername()) &&
                isValidCountry(model.getCountry());
    }

    @Override
    public boolean isValidPartner(PartnerRegisterServiceModel model) {
        return isValidUser(model.getPassword(), model.getConfirmPassword(), model.getEmail(), model.getUsername()) &&
                isValidName(model.getName()) && isValidDescription(model.getDescription());
    }

    public boolean isValidDescription(String description) {
        return !description.isEmpty() && !description.isBlank();
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isPasswordValid(String password, String confirmPassword) {
        return password.equals(confirmPassword) && !password.equals("");
    }

    public boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches() && !userRepository.existsByEmail(email);
    }

    public boolean isValidCountry(String country) {
        return !country.isBlank() && !country.isEmpty();
    }

    public boolean isValidName(String name) {
        return !name.isEmpty() && !name.isBlank();
    }

    @Override
    public boolean isValidEditClient(Client client, ClientEditModel model) {
        return (isEmailValid(model.getEmail()) || model.getEmail().equals(client.getEmail())) &&
                isPasswordValid(model.getPassword(), model.getConfirmPassword()) &&
                isValidCountry(model.getCountry());
    }

    @Override
    public boolean isValidEditPartner(Partner partner, PartnerEditModel model) {
        return (isEmailValid(model.getEmail()) || model.getEmail().equals(partner.getEmail())) &&
                isPasswordValid(model.getPassword(), model.getConfirmPassword()) &&
                (isValidName(model.getName()) || model.getName().equals(partner.getName())) &&
                isValidDescription(model.getDescription());
    }

    @Override
    public boolean isValidEditGuide(Guide guide, GuideEditModel model) {
        return (isEmailValid(model.getEmail())||model.getEmail().equals(guide.getEmail()))&&
                isPasswordValid(model.getPassword(), model.getConfirmPassword())&&
                (isValidName(model.getName())||model.getName().equals(guide.getName()));
    }
}
