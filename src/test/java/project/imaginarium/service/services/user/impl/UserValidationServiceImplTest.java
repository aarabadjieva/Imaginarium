package project.imaginarium.service.services.user.impl;

import com.project.imaginarium.ImaginariumApplicationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import project.imaginarium.data.models.users.User;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.service.models.user.ClientRegisterServiceModel;
import project.imaginarium.service.models.user.PartnerRegisterServiceModel;
import project.imaginarium.service.services.user.UserValidationService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserValidationServiceImplTest extends ImaginariumApplicationTests {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserValidationService service;

    private ModelMapper mapper = new ModelMapper();

    private User user;

    @BeforeEach
    void setupTest() {
        user = new User();
        user.setUsername("pesho");
        user.setPassword("password");
        user.setEmail("pesho@makarona.com");
        mapper = new ModelMapper();
    }

    @Test
    void returnTrueWhenUserIsValid() {
        boolean isValidUser = service.isValidUser(user.getPassword(), user.getPassword(), user.getEmail(), user.getUsername());
        assertTrue(isValidUser);
    }

    @Test
    void returnFalseWithWrongPassword(){
        boolean isValidUser = service.isValidUser(user.getPassword(), "Password", user.getEmail(), user.getUsername());
        assertFalse(isValidUser);
    }

    @Test
    void returnFalseWithWrongEmail(){
        user.setEmail("peshomakarona.com");
        boolean isValidUser = service.isValidUser(user.getPassword(), user.getPassword(), user.getEmail(), user.getUsername());
        assertFalse(isValidUser);
    }

    @Test
    void returnFalseWithUsernameTaken(){
        Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        boolean isValidUser = service.isValidUser(user.getPassword(), user.getPassword(), user.getEmail(), user.getUsername());
        assertFalse(isValidUser);
    }

    @Test
    void returnFalseWithEmailTaken(){
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        boolean isValidUser = service.isValidUser(user.getPassword(), user.getPassword(), user.getEmail(), user.getUsername());
        assertFalse(isValidUser);
    }

    @Test
    void returnTrueWhenClientCountryIsValid() {
        ClientRegisterServiceModel client = mapper.map(user, ClientRegisterServiceModel.class);
        client.setConfirmPassword(user.getPassword());
        client.setCountry("Country");
        boolean isValidClient = service.isValidClient(client);
        assertTrue(isValidClient);
    }

    @Test
    void returnFalseWhenClientCountryIs_NOT_Valid() {
        ClientRegisterServiceModel client = mapper.map(user, ClientRegisterServiceModel.class);
        client.setConfirmPassword(user.getPassword());
        client.setCountry("");
        boolean isValidClient = service.isValidClient(client);
        assertFalse(isValidClient);
    }

    @Test
    void returnTrueWhenPartnerNameAndDescriptionIsValid() {
        PartnerRegisterServiceModel partner = mapper.map(user, PartnerRegisterServiceModel.class);
        partner.setConfirmPassword(user.getPassword());
        partner.setName("someName");
        partner.setDescription("Some description");
        boolean isValidPartner = service.isValidPartner(partner);
        assertTrue(isValidPartner);
    }

    @Test
    void returnFalseWhenPartnerNameIs_NOT_Valid() {
        PartnerRegisterServiceModel partner = mapper.map(user, PartnerRegisterServiceModel.class);
        partner.setConfirmPassword(user.getPassword());
        partner.setName("");
        partner.setDescription("Some description");
        boolean isValidPartner = service.isValidPartner(partner);
        assertFalse(isValidPartner);
    }

    @Test
    void returnFalseWhenPartnerDescriptionIs_NOT_Valid() {
        PartnerRegisterServiceModel partner = mapper.map(user, PartnerRegisterServiceModel.class);
        partner.setConfirmPassword(user.getPassword());
        partner.setName("someName");
        partner.setDescription("   ");
        boolean isValidPartner = service.isValidPartner(partner);
        assertFalse(isValidPartner);
    }

}