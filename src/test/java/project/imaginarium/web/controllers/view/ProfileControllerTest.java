package project.imaginarium.web.controllers.view;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import project.imaginarium.base.ImaginariumApplicationBaseTests;
import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.offers.Offer;
import project.imaginarium.data.models.users.Client;
import project.imaginarium.data.models.users.Guide;
import project.imaginarium.data.models.users.Partner;
import project.imaginarium.data.models.users.User;
import project.imaginarium.data.repositories.OfferRepository;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.services.CloudinaryService;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static project.imaginarium.web.view.controllers.ProfileController.*;

@AutoConfigureMockMvc
class ProfileControllerTest extends ImaginariumApplicationBaseTests {

    @MockBean
    UserRepository mockUserRepo;

    @MockBean
    OfferRepository mockOfferRepo;

    @MockBean
    CloudinaryService mockCloudinaryService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getUserProfile_shouldReturnClientProfileIfUserIsClientWithStatus200() throws Exception {
        User client = new User();
        client.setUsername("username");
        Mockito.when(mockUserRepo.findByUsername(client.getUsername())).thenReturn(Optional.of(client));
        mockMvc.perform(get("/profile/client/" + client.getUsername())
                .param("username", client.getUsername()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("client"))
                .andExpect(view().name(CLIENT_PROFILE_VIEW_NAME));
    }

    @Test
    void getUserProfile_shouldReturnPartnerProfileIfUserIsPartnerWithStatus200() throws Exception {
        MockHttpSession session = new MockHttpSession();
        User partner = new User();
        partner.setUsername("username");
        partner.setSector(Sector.HOTEL);
        session.setAttribute("user", partner);
        Mockito.when(mockUserRepo.findByUsername(partner.getUsername())).thenReturn(Optional.of(partner));
        mockMvc.perform(get("/profile/partner/" + partner.getUsername())
                .param("username", partner.getUsername())
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("partner"))
                .andExpect(view().name(PARTNER_PROFILE_VIEW_NAME));
    }

    @Test
    void getUserProfile_shouldReturnGuideProfileIfUserIsGuideWithStatus200() throws Exception {
        MockHttpSession session = new MockHttpSession();
        Guide guide = new Guide();
        guide.setUsername("username");
        guide.setPlanet(Planet.Asbleg);
        session.setAttribute("user", guide);
        Mockito.when(mockUserRepo.findByUsername(guide.getUsername())).thenReturn(Optional.of(guide));
        mockMvc.perform(get("/profile/guide/" + guide.getUsername())
                .param("username", guide.getUsername())
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("guide"))
                .andExpect(view().name(GUIDE_PROFILE_VIEW_NAME));
    }

    @Test
    void getUserProfile_shouldReturnAdminProfileIfUserIsAdminWithStatus200() throws Exception {
        User admin = new User();
        admin.setUsername("username");
        Mockito.when(mockUserRepo.findByUsername(admin.getUsername())).thenReturn(Optional.of(admin));
        mockMvc.perform(get("/profile/admin/" + admin.getUsername())
                .param("username", admin.getUsername()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("admin"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(view().name(ADMIN_PROFILE_VIEW_NAME));
    }

    @Test
    void getUserProfile_shouldThrowIfUser_DO_NOT_ExistWith404() throws Exception {
        User user = new User();
        user.setUsername("username");
        Mockito.when(mockUserRepo.findByUsername(user.getUsername())).thenThrow(NoSuchUser.class);
        mockMvc.perform(get("/profile/client/" + user.getUsername())
                .param("username", user.getUsername()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));
        mockMvc.perform(get("/profile/partner/" + user.getUsername())
                .param("username", user.getUsername()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));
        mockMvc.perform(get("/profile/guide/" + user.getUsername())
                .param("username", user.getUsername()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));
        mockMvc.perform(get("/profile/admin/" + user.getUsername())
                .param("username", user.getUsername()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));
    }

    @Test
    void logout_shouldInvalidateSessionAndRedirectToHomePage() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", "user");
        mockMvc.perform(post("/profile/logout")
                .session(session))
                .andExpect(request().sessionAttributeDoesNotExist("user"))
                .andExpect(view().name("redirect:/"));

    }

    @Test
    void editProfile_shouldReturnEditClientViewIfUserExistsAndIsClient() throws Exception {
        Client client = new Client();
        client.setUsername("username");
        Mockito.when(mockUserRepo.findByUsername(client.getUsername())).thenReturn(Optional.of(client));
        mockMvc.perform(get("/profile/edit/client/" + client.getUsername()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attributeExists("countries"))
                .andExpect(view().name(CLIENT_EDIT_PROFILE_VIEW_NAME));
    }

    @Test
    void editProfile_shouldReturnEditPartnerViewIfUserExistsAndIsPartner() throws Exception {
        Partner partner = new Partner();
        partner.setUsername("username");
        Mockito.when(mockUserRepo.findByUsername(partner.getUsername())).thenReturn(Optional.of(partner));
        mockMvc.perform(get("/profile/edit/partner/" + partner.getUsername()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("partner"))
                .andExpect(view().name(PARTNER_EDIT_PROFILE_VIEW_NAME));
    }

    @Test
    void editProfile_shouldReturnEditGuideViewIfUserExistsAndIsGuide() throws Exception {
        Guide guide = new Guide();
        guide.setUsername("username");
        Mockito.when(mockUserRepo.findByUsername(guide.getUsername())).thenReturn(Optional.of(guide));
        mockMvc.perform(get("/profile/edit/guide/" + guide.getUsername()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("guide"))
                .andExpect(view().name(GUIDE_EDIT_PROFILE_VIEW_NAME));
    }

    @Test
    void editProfile_shouldThrowIfUser_DOES_NOT_Exist() throws Exception {
        User user = new User();
        user.setUsername("username");
        Mockito.when(mockUserRepo.findByUsername(user.getUsername())).thenThrow(NoSuchUser.class);
        mockMvc.perform(get("/profile/edit/client/" + user.getUsername()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));
        mockMvc.perform(get("/profile/edit/partner/" + user.getUsername()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));
        mockMvc.perform(get("/profile/edit/guide/" + user.getUsername()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));
    }

    @Test
    void updateProfile_shouldRedirectToClientProfileIfUpdatesAreValidAndUserIsClient() throws Exception {
        Client client = new Client();
        client.setUsername("username");

        Mockito.when(mockUserRepo.findByUsername(client.getUsername())).thenReturn(Optional.of(client));
        mockMvc.perform(post("/profile/edit/client/" + client.getUsername())
                .param("username", client.getUsername())
                .param("password", "pass")
                .param("confirmPassword", "pass")
                .param("email", "mail@mail.com")
                .param("country", "Bulgaria"))
                .andExpect(model().attributeExists("client"))
                .andExpect(view().name("redirect:/profile/client/" + client.getUsername()));
    }

    @Test
    void updateProfile_shouldRedirectToPartnerProfileIfUpdatesAreValidAndUserIsPartner() throws Exception {
        Partner partner = new Partner();
        partner.setUsername("username");

        Mockito.when(mockUserRepo.findByUsername(partner.getUsername())).thenReturn(Optional.of(partner));
        mockMvc.perform(post("/profile/edit/partner/" + partner.getUsername())
                .param("username", partner.getUsername())
                .param("password", "pass")
                .param("confirmPassword", "pass")
                .param("email", "mail@mail.com")
                .param("name", "name")
                .param("description", "description"))
                .andExpect(model().attributeExists("partner"))
                .andExpect(view().name("redirect:/profile/partner/" + partner.getUsername()));
    }

    @Test
    void updateProfile_shouldRedirectToGuideProfileIfUpdatesAreValidAndUserIsGuide() throws Exception {
        Guide guide = new Guide();
        guide.setUsername("username");

        Mockito.when(mockUserRepo.findByUsername(guide.getUsername())).thenReturn(Optional.of(guide));
        mockMvc.perform(post("/profile/edit/guide/" + guide.getUsername())
                .param("username", guide.getUsername())
                .param("password", "pass")
                .param("confirmPassword", "pass")
                .param("email", "mail@mail.com")
                .param("name", "name"))
                .andExpect(model().attributeExists("guide"))
                .andExpect(view().name("redirect:/profile/guide/" + guide.getUsername()));
    }

    @Test
    void updateProfile_shouldReturnToProfileEditRoleIfUpdates_ARE_NOT_Valid() throws Exception {
        Client client = new Client();
        client.setUsername("usernameClient");

        Mockito.when(mockUserRepo.findByUsername(client.getUsername())).thenReturn(Optional.of(client));
        mockMvc.perform(post("/profile/edit/client/" + client.getUsername())
                .param("username", client.getUsername())
                .param("password", "pass")
                .param("confirmPassword", "wrong_pass") //not the same password and confirm password
                .param("email", "mail@mail.com")
                .param("country", "Bulgaria"))
                .andExpect(view().name("redirect:/profile/edit/client/" + client.getUsername()));

        Partner partner = new Partner();
        partner.setUsername("usernamePartner");

        Mockito.when(mockUserRepo.findByUsername(partner.getUsername())).thenReturn(Optional.of(partner));

        mockMvc.perform(post("/profile/edit/partner/" + partner.getUsername())
                .param("username", partner.getUsername())
                .param("password", "pass")
                .param("confirmPassword", "pass")
                .param("email", "mailmail.com") //missing '@' from email
                .param("name", "name")
                .param("description", "description"))
                .andExpect(view().name("redirect:/profile/edit/partner/" + partner.getUsername()));

        Guide guide = new Guide();
        guide.setUsername("usernameGuide");

        Mockito.when(mockUserRepo.findByUsername(guide.getUsername())).thenReturn(Optional.of(guide));
        mockMvc.perform(post("/profile/edit/guide/" + guide.getUsername())
                .param("username", guide.getUsername())
                .param("password", "pass")
                .param("confirmPassword", "pass")
                .param("email", "mail@mail.com")
                .param("name", ""))  //name is blank/empty
                .andExpect(view().name("redirect:/profile/edit/guide/" + guide.getUsername()));
    }


    @Test
    void saveOffer_shouldRedirectToUserProfilePage() throws Exception {
        Client client = new Client();
        client.setUsername("username");
        Offer offer = new Offer();
        offer.setName("offerName");

        Mockito.when(mockUserRepo.findByUsername(client.getUsername())).thenReturn(Optional.of(client));
        Mockito.when(mockOfferRepo.findByName(offer.getName())).thenReturn(Optional.of(offer));
        mockMvc.perform(get("/profile/" + client.getUsername() + "/save/" + offer.getName()))
                .andExpect(view().name("redirect:/profile/client/" + client.getUsername()));
    }

    @Test
    void createAdmin_shouldRedirectToCurrentAdminProfileWith302() throws Exception {
        User user = new User();
        user.setUsername("username");
        User admin = new User();
        admin.setUsername("admin");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("username", admin.getUsername());

        Mockito.when(mockUserRepo.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/profile/create/admin/" + user.getUsername())
                .session(session))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/profile/admin/" + admin.getUsername()));
    }

    @Test
    void deleteAdmin_shouldRedirectToCurrentAdminProfileWith302() throws Exception {
        User user = new User();
        user.setUsername("username");
        User admin = new User();
        admin.setUsername("admin");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("username", admin.getUsername());

        Mockito.when(mockUserRepo.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/profile/create/admin/" + user.getUsername())
                .session(session))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/profile/admin/" + admin.getUsername()));
    }
}