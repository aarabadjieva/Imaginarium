package project.imaginarium.service.services.user;

import project.imaginarium.service.models.user.*;
import project.imaginarium.web.models.user.edit.ClientEditModel;
import project.imaginarium.web.models.user.edit.GuideEditModel;
import project.imaginarium.web.models.user.edit.PartnerEditModel;
import project.imaginarium.web.models.user.view.GuideViewModel;
import project.imaginarium.web.models.user.view.PartnerViewModel;

import java.util.List;

public interface UserService {
    void saveClient(ClientRegisterServiceModel serviceModel) throws Exception;

    void savePartner(PartnerRegisterServiceModel serviceModel) throws Exception;

    UserLoggedServiceModel login(UserServiceLoginModel serviceModel) throws Exception;

    ClientServiceModel findClientByUsername(String name);

    PartnerServiceModel findPartnerByUsername(String name);

    GuideServiceModel findGuideByUsername(String name);

    List<GuideViewModel> guides();

    List<PartnerViewModel> partners();

    void updateClient(ClientEditModel model) throws Exception;

    void updatePartner(PartnerEditModel partner) throws Exception;

    void updateGuide(GuideEditModel guide) throws Exception;

    void clientAddOffer(String user, String offer);

    List<UserServiceModel> findAllUsers();

    void makeAdmin(String name);

    void deleteAdmin(String name);
}
