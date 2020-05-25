package project.imaginarium.service.services.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.imaginarium.base.ImaginariumApplicationBaseTests;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.offers.Offer;
import project.imaginarium.data.models.users.Client;
import project.imaginarium.data.models.users.Partner;
import project.imaginarium.data.models.users.Role;
import project.imaginarium.data.models.users.User;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.exeptions.NoSuchOffer;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.models.user.*;
import project.imaginarium.service.services.OffersService;
import project.imaginarium.service.services.RoleService;
import project.imaginarium.web.api.models.user.response.GuideResponseModel;
import project.imaginarium.web.api.models.user.response.PartnerResponseModel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static project.imaginarium.exeptions.ExceptionMessage.USER_NOT_FOUND_MESSAGE;

class UserServiceTest extends ImaginariumApplicationBaseTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserValidationService userValidationService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private OffersService offersService;

    @MockBean
    private BCryptPasswordEncoder encoder;

    private ModelMapper mapper;

    @Autowired
    private UserService service;

    private User user;

    @BeforeEach
    void setupTest(){
        user = new User();
        user.setUsername("pesho");
        user.setPassword("pass");
        user.setEmail("pesho@makarona.com");
        mapper = new ModelMapper();
    }

    @Test
    void saveClient_shouldSaveClientAsAdminIfFirstUserAndValid() throws Exception {
        ClientRegisterServiceModel registerServiceModel = mapper.map(user, ClientRegisterServiceModel.class);
        Role role = new Role("ADMIN");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.count()).thenReturn(0L);
        Mockito.when(encoder.encode(registerServiceModel.getPassword())).thenReturn("pass");
        Mockito.when(userValidationService.isValidClient(registerServiceModel)).thenReturn(true);
        service.saveClient(registerServiceModel);
        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        Mockito.verify(userRepository).saveAndFlush(captor.capture());
        Client entity = captor.getValue();
        assertNotNull(entity);
        assertEquals(entity.getEmail(), registerServiceModel.getEmail());
        assertTrue(entity.getAuthorities().contains(role));
    }

    @Test
    void saveClient_shouldSaveClientAsClientIf_NOT_FirstUserAndValid() throws Exception {
        ClientRegisterServiceModel registerServiceModel = mapper.map(user, ClientRegisterServiceModel.class);
        Role role = new Role("CLIENT");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.count()).thenReturn(4L);
        Mockito.when(encoder.encode(registerServiceModel.getPassword())).thenReturn("pass");
        Mockito.when(userValidationService.isValidClient(registerServiceModel)).thenReturn(true);
        service.saveClient(registerServiceModel);
        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        Mockito.verify(userRepository).saveAndFlush(captor.capture());
        Client entity = captor.getValue();
        assertNotNull(entity);
        assertEquals(entity.getEmail(), registerServiceModel.getEmail());
        assertTrue(entity.getAuthorities().contains(role));
    }

    @Test
    void saveClient_shouldThrowExceptionIfClient_NOT_Valid() throws Exception {
        ClientRegisterServiceModel registerServiceModel = mapper.map(user, ClientRegisterServiceModel.class);
        Role role = new Role("ADMIN");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.count()).thenReturn(0L);
        Mockito.when(encoder.encode(registerServiceModel.getPassword())).thenReturn("pass");
        Mockito.when(userValidationService.isValidClient(registerServiceModel)).thenReturn(false);
        assertThrows(Exception.class, () -> service.saveClient(registerServiceModel));
    }

    @Test
    void savePartner_shouldSavePartnerIfValid() throws Exception {
        PartnerRegisterServiceModel registerServiceModel = mapper.map(user, PartnerRegisterServiceModel.class);
        registerServiceModel.setSector(Sector.HOTEL);
        Role role = new Role("ADMIN");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.count()).thenReturn(0L);
        Mockito.when(encoder.encode(registerServiceModel.getPassword())).thenReturn("pass");
        Mockito.when(userValidationService.isValidPartner(registerServiceModel)).thenReturn(true);
        service.savePartner(registerServiceModel);
        ArgumentCaptor<Partner> captor = ArgumentCaptor.forClass(Partner.class);
        Mockito.verify(userRepository).saveAndFlush(captor.capture());
        Partner entity = captor.getValue();
        assertNotNull(entity);
        assertEquals(entity.getEmail(), registerServiceModel.getEmail());
        assertTrue(entity.getAuthorities().contains(role));
    }

    @Test
    void savePartner_shouldThrowExceptionIfPartnerIs_NOT_Valid() throws Exception {
        PartnerRegisterServiceModel registerServiceModel = mapper.map(user, PartnerRegisterServiceModel.class);
        registerServiceModel.setSector(Sector.HOTEL);
        Role role = new Role("PARTNER");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.count()).thenReturn(4L);
        Mockito.when(encoder.encode(registerServiceModel.getPassword())).thenReturn("pass");
        Mockito.when(userValidationService.isValidPartner(registerServiceModel)).thenReturn(false);
        assertThrows(Exception.class, () -> service.savePartner(registerServiceModel));
    }

    @Test
    void login_shouldReturnUserIfUsernameAndPasswordCorrect() throws Exception {
        UserServiceLoginModel loginUser = mapper.map(user, UserServiceLoginModel.class);
        Mockito.when(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .thenReturn(Optional.ofNullable(user));
        Mockito.when(encoder.encode(user.getPassword())).thenReturn("pass");
        UserLoggedServiceModel loggedUser = service.login(loginUser);
        assertEquals(loggedUser.getUsername(), loginUser.getUsername());
    }

    @Test
    void login_shouldThrowIfUsernameAndPassword_NOT_Correct() throws Exception {
        UserServiceLoginModel loginUser = mapper.map(user, UserServiceLoginModel.class);
        Mockito.when(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .thenThrow(new NoSuchUser(USER_NOT_FOUND_MESSAGE));
        Mockito.when(encoder.encode(user.getPassword())).thenReturn("pass");
        assertThrows(NoSuchUser.class, () -> service.login(loginUser));
    }

    @Test
    void findClientByUsername_shouldReturnClientIfExist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        ClientServiceModel client = service.findClientByUsername(user.getUsername());
        assertEquals(client.getUsername(), user.getUsername());
    }

    @Test
    void findClientByUsername_shouldThrowIfClient_NOT_Exist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenThrow(NoSuchUser.class);
        assertThrows(NoSuchUser.class, () -> service.findClientByUsername(user.getUsername()));
    }

    @Test
    void findPartnerByUsername_shouldReturnPartnerIfExist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        PartnerServiceModel partner = service.findPartnerByUsername(user.getUsername());
        assertEquals(partner.getUsername(), user.getUsername());
    }

    @Test
    void findPartnerByUsername_shouldThrowIfPartner_NOT_Exist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenThrow(NoSuchUser.class);
        assertThrows(NoSuchUser.class, () -> service.findPartnerByUsername(user.getUsername()));
    }

    @Test
    void findGuideByUsername_shouldReturnGuideIfExists() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        GuideServiceModel guide = service.findGuideByUsername(user.getUsername());
        assertEquals(guide.getUsername(), user.getUsername());
    }

    @Test
    void findGuideByUsername_shouldThrowIfGuide_NOT_Exist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenThrow(NoSuchUser.class);
        assertThrows(NoSuchUser.class, () -> service.findGuideByUsername(user.getUsername()));
    }

    @Test
    void guides_shouldReturnAllGuides() {
        List<User> guides = getDummyUsers(3);
        Role role = new Role("GUIDE");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.findAllByAuthoritiesContaining(role)).thenReturn(guides);
        List<GuideResponseModel> models = service.guides();
        assertEquals(guides.size(), models.size());
        assertEquals(guides.get(0).getUsername(), models.get(0).getUsername());
        assertEquals(guides.get(1).getUsername(), models.get(1).getUsername());
        assertEquals(guides.get(2).getUsername(), models.get(2).getUsername());
    }

    @Test
    void partners_shouldReturnAllPartners() {
        List<User> partners = getDummyUsers(3);
        Role role = new Role("PARTNER");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.findAllByAuthoritiesContaining(role)).thenReturn(partners);
        List<PartnerResponseModel> models = service.partners();
        assertEquals(partners.size(), models.size());
        assertEquals(partners.get(0).getUsername(), models.get(0).getUsername());
        assertEquals(partners.get(1).getUsername(), models.get(1).getUsername());
        assertEquals(partners.get(2).getUsername(), models.get(2).getUsername());
    }


    @Test
    void clientAddOffer_shouldAddOfferToClientWhenBothExist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        Offer offer = new Offer();
        offer.setName("Offer");
        Mockito.when(offersService.findOfferByName(offer.getName())).thenReturn(offer);
        service.clientAddOffer(user.getUsername(), offer.getName());
        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        Mockito.verify(userRepository).saveAndFlush(captor.capture());
        Client entity = captor.getValue();
        Assertions.assertEquals(entity.getOffers().get(0), offer);
    }

    @Test
    void clientAddOffer_shouldThrowWhenClient_NOT_Exist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenThrow(NoSuchUser.class);
        Offer offer = new Offer();
        offer.setName("Offer");
        assertThrows(NoSuchUser.class, () -> service.clientAddOffer(user.getUsername(), offer.getName()));
    }

    @Test
    void clientAddOffer_shouldThrowWhenOffer_NOT_Exist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        Offer offer = new Offer();
        offer.setName("Offer");
        Mockito.when(offersService.findOfferByName(offer.getName())).thenThrow(NoSuchOffer.class);
        assertThrows(NoSuchOffer.class, () -> service.clientAddOffer(user.getUsername(), offer.getName()));
    }

    @Test
    void findAllUsers_shouldReturnAllUsers() {
        List<User> users = getDummyUsers(3);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<UserServiceModel> models = service.findAllUsers();
        assertEquals(users.size(), models.size());
        assertEquals(users.get(0).getUsername(), models.get(0).getUsername());
        assertEquals(users.get(1).getUsername(), models.get(1).getUsername());
        assertEquals(users.get(2).getUsername(), models.get(2).getUsername());
    }

    @Test
    void makeAdmin_shouldAddRoleADMIN_ToUserAuthorities() {
        Role role = new Role("ADMIN");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        service.makeAdmin(user.getUsername());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).saveAndFlush(captor.capture());
        User entity = captor.getValue();
        assertTrue(entity.getAuthorities().contains(role));
    }

    @Test
    void deleteAdmin_shouldRemoveRoleADMIN_FromUserAuthorities() {
        Role role = new Role("ADMIN");
        user.getAuthorities().add(role);
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        service.deleteAdmin(user.getUsername());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).saveAndFlush(captor.capture());
        User entity = captor.getValue();
        assertFalse(entity.getAuthorities().contains(role));
    }

    private List<User> getDummyUsers(int count) {
        return IntStream.range(0, count)
                .map(x -> x + 1)
                .mapToObj(id -> {
                    User user = new User();
                    user.setId(String.valueOf(id));
                    user.setUsername("User " + id);
                    user.setPassword("pass");
                    user.setEmail("mail@mail.bg");
                    user.setSector(Sector.CLIENT);
                    user.setAuthorities(Collections.singleton(new Role("ROLE")));
                    return user;
                })
                .collect(Collectors.toList());
    }
}