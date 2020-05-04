package project.imaginarium.service.services.user.validation;

import project.imaginarium.base.ImaginariumApplicationBaseTests;
import project.imaginarium.data.models.users.User;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.service.services.user.UserValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import project.imaginarium.service.models.user.ClientRegisterServiceModel;
import project.imaginarium.service.models.user.PartnerRegisterServiceModel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserValidationServiceTest extends ImaginariumApplicationBaseTests {

    @MockBean
    private UserRepository userRepository;

    private ModelMapper mapper;

    private User user;

    @Autowired
    private UserValidationService service;

    @Override
    protected void beforeEach() {
        user = new User();
        user.setUsername("pesho");
        user.setPassword("password");
        user.setEmail("pesho@makarona.com");
        mapper = new ModelMapper();
    }

    @Test
    void isValidUser_shouldReturnTrueWhenUserIsValid() {
        boolean isValidUser = service.isValidUser(user.getPassword(), user.getPassword(), user.getEmail(), user.getUsername());
        assertTrue(isValidUser);
    }

    @Test
    void isValidUser_shouldReturnFalseWithWrongPassword(){
        boolean isValidUser = service.isValidUser(user.getPassword(), "Password", user.getEmail(), user.getUsername());
        assertFalse(isValidUser);
    }

    @Test
    void isValidUser_shouldReturnFalseWithWrongEmail(){
        user.setEmail("peshomakarona.com");
        boolean isValidUser = service.isValidUser(user.getPassword(), user.getPassword(), user.getEmail(), user.getUsername());
        assertFalse(isValidUser);
    }

    @Test
    void isValidUser_shouldReturnFalseWithUsernameTaken(){
        Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        boolean isValidUser = service.isValidUser(user.getPassword(), user.getPassword(), user.getEmail(), user.getUsername());
        assertFalse(isValidUser);
    }

    @Test
    void isValidUser_shouldReturnFalseWithEmailTaken(){
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        boolean isValidUser = service.isValidUser(user.getPassword(), user.getPassword(), user.getEmail(), user.getUsername());
        assertFalse(isValidUser);
    }

    @Test
    void isValidClient_shouldReturnTrueWhenClientCountryIsValid() {
        ClientRegisterServiceModel client = mapper.map(user, ClientRegisterServiceModel.class);
        client.setConfirmPassword(user.getPassword());
        client.setCountry("Country");
        boolean isValidClient = service.isValidClient(client);
        assertTrue(isValidClient);
    }

    @Test
    void isValidClient_shouldReturnFalseWhenClientCountryIs_NOT_Valid() {
        ClientRegisterServiceModel client = mapper.map(user, ClientRegisterServiceModel.class);
        client.setConfirmPassword(user.getPassword());
        client.setCountry("");
        boolean isValidClient = service.isValidClient(client);
        assertFalse(isValidClient);
    }

    @Test
    void isValidPartner_shouldReturnTrueWhenPartnerNameAndDescriptionIsValid() {
        PartnerRegisterServiceModel partner = mapper.map(user, PartnerRegisterServiceModel.class);
        partner.setConfirmPassword(user.getPassword());
        partner.setName("someName");
        partner.setDescription("Some description");
        boolean isValidPartner = service.isValidPartner(partner);
        assertTrue(isValidPartner);
    }

    @Test
    void isValidPartner_shouldReturnFalseWhenPartnerNameIs_NOT_Valid() {
        PartnerRegisterServiceModel partner = mapper.map(user, PartnerRegisterServiceModel.class);
        partner.setConfirmPassword(user.getPassword());
        partner.setName("");
        partner.setDescription("Some description");
        boolean isValidPartner = service.isValidPartner(partner);
        assertFalse(isValidPartner);
    }

    @Test
    void isValidPartner_shouldReturnFalseWhenPartnerDescriptionIs_NOT_Valid() {
        PartnerRegisterServiceModel partner = mapper.map(user, PartnerRegisterServiceModel.class);
        partner.setConfirmPassword(user.getPassword());
        partner.setName("someName");
        partner.setDescription("   ");
        boolean isValidPartner = service.isValidPartner(partner);
        assertFalse(isValidPartner);
    }

}