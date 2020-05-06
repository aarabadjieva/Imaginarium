package project.imaginarium.web.controllers.view;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import project.imaginarium.base.ImaginariumApplicationBaseTests;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.users.Role;
import project.imaginarium.data.models.users.User;
import project.imaginarium.data.repositories.RoleRepository;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.services.CloudinaryService;
import project.imaginarium.service.services.user.HashingService;

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
    HashingService mockHashingService;

    @MockBean
    RoleRepository mockRoleRepo;

    @Autowired
    MockMvc mockMvc;


    @Test
    void getClientRegister_shouldReturnClientRegisterViewWithCountriesAttributeAndStatus200() throws Exception {
        mockMvc.perform(get("/users/register/user/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("countries"))
                .andExpect(view().name(USERS_REGISTER_CLIENT_VIEW_NAME));
    }

    @Test
    void createClient_shouldRedirectToHomePageAndSetSessionAttributesAndStatus302IfModel_HAS_NO_EmptyFields() throws Exception {
        Mockito.when(mockUserRepo.count()).thenReturn(5L);
        Mockito.when(mockHashingService.hash("pass")).thenReturn("pass");
        mockMvc.perform(post("/users/register/user")
                .param("username", "username")
                .param("email", "mail@mail.com")
                .param("password", "pass")
                .param("confirmPassword", "pass")
                .param("country", "Bulgaria"))
                .andExpect(status().isFound())
                .andExpect(request().sessionAttribute("username", "username"))
                .andExpect(request().sessionAttribute("role", "client"))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void createClient_shouldReturnClientRegisterViewIfClientRegisterModel_HAS_EmptyFields() throws Exception {
        Mockito.when(mockUserRepo.count()).thenReturn(5L);
        mockMvc.perform(post("/users/register/user")
                .param("username", "username") //missing email
                .param("password", "pass")
                .param("confirmPassword", "pass")
                .param("country", "Bulgaria"))
                .andExpect(status().isOk())
                .andExpect(view().name(USERS_REGISTER_CLIENT_VIEW_NAME));
    }

    @Test
    void getPartnerRegister_shouldReturnPartnerRegisterViewWithCountriesAttributeAndStatus200() throws Exception {
        mockMvc.perform(get("/users/register/partner"))
                .andExpect(status().isOk())
                .andExpect(view().name(USERS_REGISTER_PARTNER_VIEW_NAME));
    }

    @Test
    void createPartner_shouldRedirectToHomePageAndSetSessionAttributeUsernameAndStatus302IfModel_HAS_NO_EmptyFields() throws Exception {
        Mockito.when(mockUserRepo.count()).thenReturn(5L);
        Mockito.when(mockHashingService.hash("pass")).thenReturn("pass");
        mockMvc.perform(post("/users/register/partner")
                .param("username", "username")
                .param("email", "mail@mail.com")
                .param("password", "pass")
                .param("confirmPassword", "pass")
                .param("name", "name")
                .param("description", "description")
                .param("sector", String.valueOf(Sector.HOTEL)))
                .andExpect(status().isFound())
                .andExpect(request().sessionAttribute("username", "username"))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void createPartner_shouldReturnPartnerRegisterViewIfClientRegisterModel_HAS_EmptyFields() throws Exception {
        Mockito.when(mockUserRepo.count()).thenReturn(5L);
        mockMvc.perform(post("/users/register/partner")
                .param("username", "username"))
                .andExpect(status().isOk())
                .andExpect(view().name(USERS_REGISTER_PARTNER_VIEW_NAME));
    }

    @Test
    void getLogin_shouldRedirectToClientProfileAndSetSessionAttributesIfCredentialsAreCorrectAndUserIsClientWithStatus302() throws Exception {
        User client = new User();
        client.setUsername("username");
        client.setPassword("pass");
        client.setSector(Sector.CLIENT);
        Mockito.when(mockUserRepo.findByUsernameAndPassword(client.getUsername(), client.getPassword()))
                .thenReturn(Optional.of(client));
        Mockito.when(mockHashingService.hash(client.getPassword())).thenReturn(client.getPassword());
        mockMvc.perform(post("/users/login")
        .param("username", client.getUsername())
        .param("password", client.getPassword()))
                .andExpect(status().isFound())
                .andExpect(request().sessionAttribute("username", "username"))
                .andExpect(request().sessionAttribute("role", "client"))
                .andExpect(view().name("redirect:/profile/client/"+ client.getUsername()));
    }

    @Test
    void getLogin_shouldRedirectToPartnerProfileAndSetSessionAttributesIfCredentialsAreCorrectAndUserIsPartnerWithStatus302() throws Exception {
        User partner = new User();
        partner.setUsername("username");
        partner.setPassword("pass");
        partner.setSector(Sector.HOTEL);
        Mockito.when(mockUserRepo.findByUsernameAndPassword(partner.getUsername(), partner.getPassword()))
                .thenReturn(Optional.of(partner));
        Mockito.when(mockHashingService.hash(partner.getPassword())).thenReturn(partner.getPassword());
        mockMvc.perform(post("/users/login")
                .param("username", partner.getUsername())
                .param("password", partner.getPassword()))
                .andExpect(status().isFound())
                .andExpect(request().sessionAttribute("username", "username"))
                .andExpect(request().sessionAttribute("role", "partner"))
                .andExpect(view().name("redirect:/profile/partner/"+ partner.getUsername()));
    }

    @Test
    void getLogin_shouldRedirectToGuideProfileAndSetSessionAttributesIfCredentialsAreCorrectAndUserIsGuideWithStatus302() throws Exception {
        User guide = new User();
        guide.setUsername("username");
        guide.setPassword("pass");
        guide.setSector(Sector.GUIDE);
        Mockito.when(mockUserRepo.findByUsernameAndPassword(guide.getUsername(), guide.getPassword()))
                .thenReturn(Optional.of(guide));
        Mockito.when(mockHashingService.hash(guide.getPassword())).thenReturn(guide.getPassword());
        mockMvc.perform(post("/users/login")
                .param("username", guide.getUsername())
                .param("password", guide.getPassword()))
                .andExpect(status().isFound())
                .andExpect(request().sessionAttribute("username", "username"))
                .andExpect(request().sessionAttribute("role", "guide"))
                .andExpect(view().name("redirect:/profile/guide/"+ guide.getUsername()));
    }

    @Test
    void getLogin_shouldRedirectToAdminProfileAndSetSessionAttributesIfCredentialsAreCorrectAndUserIsAdminWithStatus302() throws Exception {
        Role role = new Role("ADMIN");
        User admin = new User();
        admin.setUsername("username");
        admin.setPassword("pass");
        admin.setSector(Sector.CLIENT);
        admin.getAuthorities().add(role);
        Mockito.when(mockRoleRepo.findByAuthority(role.getAuthority())).thenReturn(Optional.of(role));
        Mockito.when(mockUserRepo.findByUsernameAndPassword(admin.getUsername(), admin.getPassword()))
                .thenReturn(Optional.of(admin));
        Mockito.when(mockHashingService.hash(admin.getPassword())).thenReturn(admin.getPassword());
        mockMvc.perform(post("/users/login")
                .param("username", admin.getUsername())
                .param("password", admin.getPassword()))
                .andExpect(status().isFound())
                .andExpect(request().sessionAttribute("username", "username"))
                .andExpect(request().sessionAttribute("role", "admin"))
                .andExpect(view().name("redirect:/profile/admin/"+ admin.getUsername()));
    }

    @Test
    void getLogin_shouldThrowIfCredentials_ARE_NOT_CorrectWith404() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("pass");
        Mockito.when(mockUserRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .thenThrow(NoSuchUser.class);
        Mockito.when(mockHashingService.hash(user.getPassword())).thenReturn(user.getPassword());
        mockMvc.perform(post("/users/login")
                .param("username", user.getUsername())
                .param("password", user.getPassword()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));
    }
}