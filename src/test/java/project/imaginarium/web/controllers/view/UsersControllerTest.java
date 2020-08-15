package project.imaginarium.web.controllers.view;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import project.imaginarium.base.ImaginariumApplicationBaseTests;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.users.Role;
import project.imaginarium.data.repositories.RoleRepository;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.service.services.CloudinaryService;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static project.imaginarium.web.view.controllers.UsersController.USERS_REGISTER_CLIENT_VIEW_NAME;
import static project.imaginarium.web.view.controllers.UsersController.USERS_REGISTER_PARTNER_VIEW_NAME;

@AutoConfigureMockMvc
class UsersControllerTest extends ImaginariumApplicationBaseTests {

    @MockBean
    UserRepository mockUserRepo;

    @MockBean
    CloudinaryService mockCloudinaryService;

    @MockBean
    BCryptPasswordEncoder mockEncoder;

    @MockBean
    RoleRepository mockRoleRepo;

    @Autowired
    MockMvc mockMvc;


    @Test
    void getClientRegister_shouldReturnClientRegisterViewWithCountriesAttributeAndStatus200() throws Exception {
        mockMvc.perform(get("/register/user"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("countries"))
                .andExpect(view().name(USERS_REGISTER_CLIENT_VIEW_NAME));
    }

    @Test
    void createClient_shouldRedirectToClientProfileIfModel_HAS_NO_EmptyFields() throws Exception {

        Mockito.when(mockUserRepo.count()).thenReturn(5L);
        Mockito.when(mockEncoder.encode("pass")).thenReturn("pass");
        Mockito.when(mockRoleRepo.findByAuthority("CLIENT")).thenReturn(Optional.of(new Role("CLIENT")));
        Mockito.when(mockRoleRepo.findByAuthority("ADMIN")).thenReturn(Optional.of(new Role("ADMIN")));
        mockMvc.perform(post("/register/user")
                .param("username", "username")
                .param("email", "mail@mail.com")
                .param("password", "pass")
                .param("confirmPassword", "pass")
                .param("country", "Bulgaria"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/profile/client/username"));
    }

    @Test
    void createClient_shouldReturnClientRegisterViewIfClientRegisterModel_HAS_EmptyFields() throws Exception {
        Mockito.when(mockUserRepo.count()).thenReturn(5L);
        mockMvc.perform(post("/register/user")
                .param("username", "username") //missing email
                .param("password", "pass")
                .param("confirmPassword", "pass")
                .param("country", "Bulgaria"))
                .andExpect(status().isOk())
                .andExpect(view().name(USERS_REGISTER_CLIENT_VIEW_NAME));
    }

    @Test
    void getPartnerRegister_shouldReturnPartnerRegisterViewWithCountriesAttributeAndStatus200() throws Exception {
        mockMvc.perform(get("/register/partner"))
                .andExpect(status().isOk())
                .andExpect(view().name(USERS_REGISTER_PARTNER_VIEW_NAME));
    }

    @Test
    void createPartner_shouldRedirectToPartnerProfileAndStatus302IfModel_HAS_NO_EmptyFields() throws Exception {
        Mockito.when(mockUserRepo.count()).thenReturn(5L);
        Mockito.when(mockEncoder.encode("pass")).thenReturn("pass");
        Mockito.when(mockRoleRepo.findByAuthority("PARTNER")).thenReturn(Optional.of(new Role("PARTNER")));
        Mockito.when(mockRoleRepo.findByAuthority("ADMIN")).thenReturn(Optional.of(new Role("ADMIN")));
        mockMvc.perform(post("/register/partner")
                .param("username", "username")
                .param("email", "mail@mail.com")
                .param("password", "pass")
                .param("confirmPassword", "pass")
                .param("name", "name")
                .param("description", "description")
                .param("sector", String.valueOf(Sector.HOTEL)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/profile/partner/username"));
    }

    @Test
    void createPartner_shouldReturnPartnerRegisterViewIfClientRegisterModel_HAS_EmptyFields() throws Exception {
        Mockito.when(mockUserRepo.count()).thenReturn(5L);
        mockMvc.perform(post("/register/partner")
                .param("username", "username"))
                .andExpect(status().isOk())
                .andExpect(view().name(USERS_REGISTER_PARTNER_VIEW_NAME));
    }


}