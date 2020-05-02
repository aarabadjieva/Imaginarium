package project.imaginarium.service.services.user;

import project.imaginarium.data.models.users.Client;
import project.imaginarium.data.models.users.Guide;
import project.imaginarium.data.models.users.Partner;
import project.imaginarium.service.models.user.ClientRegisterServiceModel;
import project.imaginarium.service.models.user.PartnerRegisterServiceModel;
import project.imaginarium.web.view.models.user.edit.ClientEditModel;
import project.imaginarium.web.view.models.user.edit.GuideEditModel;
import project.imaginarium.web.view.models.user.edit.PartnerEditModel;

public interface UserValidationService {

    boolean isValidUser(String password, String confirmPassword, String email, String username);

    boolean isValidClient(ClientRegisterServiceModel model);

    boolean isValidPartner(PartnerRegisterServiceModel model);

    boolean isValidEditClient(Client client, ClientEditModel model);

    boolean isValidEditPartner(Partner partner, PartnerEditModel model);

    boolean isValidEditGuide(Guide guide, GuideEditModel model);
}
