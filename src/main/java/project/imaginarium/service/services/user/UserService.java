package project.imaginarium.service.services.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import project.imaginarium.service.models.user.*;
import project.imaginarium.web.api.models.user.response.GuideResponseModel;
import project.imaginarium.web.api.models.user.response.PartnerResponseModel;
import project.imaginarium.web.view.models.user.edit.ClientEditModel;
import project.imaginarium.web.view.models.user.edit.GuideEditModel;
import project.imaginarium.web.view.models.user.edit.PartnerEditModel;

import java.util.List;

public interface UserService extends UserDetailsService {
    void saveClient(ClientRegisterServiceModel serviceModel) throws Exception;

    void savePartner(PartnerRegisterServiceModel serviceModel) throws Exception;

    UserLoggedServiceModel login(UserServiceLoginModel serviceModel) throws Exception;

    ClientServiceModel findClientByUsername(String name);

    PartnerServiceModel findPartnerByUsername(String name);

    GuideServiceModel findGuideByUsername(String name);

    List<GuideResponseModel> guides();

    List<PartnerResponseModel> partners();

    void updateClient(ClientEditModel model) throws Exception;

    void updatePartner(PartnerEditModel partner) throws Exception;

    void updateGuide(GuideEditModel guide) throws Exception;

    void clientAddOffer(String user, String offer);

    List<UserServiceModel> findAllUsers();

    void makeAdmin(String name);

    void deleteAdmin(String name);
}
