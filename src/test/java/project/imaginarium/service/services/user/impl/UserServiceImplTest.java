package project.imaginarium.service.services.user.impl;

import com.project.imaginarium.ImaginariumApplicationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import project.imaginarium.service.services.user.HashingService;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.service.services.user.UserValidationService;
import project.imaginarium.web.models.user.view.GuideViewModel;
import project.imaginarium.web.models.user.view.PartnerViewModel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static project.imaginarium.Common.USER_NOT_FOUND_MESSAGE;

class UserServiceImplTest extends ImaginariumApplicationTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserValidationService userValidationService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private OffersService offersService;

    @MockBean
    private HashingService hashingService;

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
    void saveClientAsAdminIfFirstUserAndValid() throws Exception {
        ClientRegisterServiceModel registerServiceModel = mapper.map(user, ClientRegisterServiceModel.class);
        Role role = new Role("ADMIN");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.count()).thenReturn(0L);
        Mockito.when(hashingService.hash(registerServiceModel.getPassword())).thenReturn("pass");
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
    void saveClientAsClientIf_NOT_FirstUserAndValid() throws Exception {
        ClientRegisterServiceModel registerServiceModel = mapper.map(user, ClientRegisterServiceModel.class);
        Role role = new Role("CLIENT");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.count()).thenReturn(4L);
        Mockito.when(hashingService.hash(registerServiceModel.getPassword())).thenReturn("pass");
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
    void shouldThrowExceptionIfClient_NOT_Valid() throws Exception {
        ClientRegisterServiceModel registerServiceModel = mapper.map(user, ClientRegisterServiceModel.class);
        Role role = new Role("ADMIN");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.count()).thenReturn(0L);
        Mockito.when(hashingService.hash(registerServiceModel.getPassword())).thenReturn("pass");
        Mockito.when(userValidationService.isValidClient(registerServiceModel)).thenReturn(false);
        assertThrows(Exception.class, () -> service.saveClient(registerServiceModel));
    }

    @Test
    void shouldSavePartnerIfValid() throws Exception {
        PartnerRegisterServiceModel registerServiceModel = mapper.map(user, PartnerRegisterServiceModel.class);
        registerServiceModel.setSector(Sector.HOTELS);
        Role role = new Role("ADMIN");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.count()).thenReturn(0L);
        Mockito.when(hashingService.hash(registerServiceModel.getPassword())).thenReturn("pass");
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
    void shouldThrowExceptionIfPartnerIs_NOT_Valid() throws Exception {
        PartnerRegisterServiceModel registerServiceModel = mapper.map(user, PartnerRegisterServiceModel.class);
        registerServiceModel.setSector(Sector.HOTELS);
        Role role = new Role("PARTNER");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.count()).thenReturn(4L);
        Mockito.when(hashingService.hash(registerServiceModel.getPassword())).thenReturn("pass");
        Mockito.when(userValidationService.isValidPartner(registerServiceModel)).thenReturn(false);
        assertThrows(Exception.class, () -> service.savePartner(registerServiceModel));
    }

    @Test
    void loginShouldReturnUserIfCredentialsAreCorrect() throws Exception {
        UserServiceLoginModel loginUser = mapper.map(user, UserServiceLoginModel.class);
        Mockito.when(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .thenReturn(Optional.ofNullable(user));
        Mockito.when(hashingService.hash(user.getPassword())).thenReturn("pass");
        UserLoggedServiceModel loggedUser = service.login(loginUser);
        assertEquals(loggedUser.getUsername(), loginUser.getUsername());
    }

    @Test
    void loginShouldThrowIfCredentialsAre_NOT_Correct() throws Exception {
        UserServiceLoginModel loginUser = mapper.map(user, UserServiceLoginModel.class);
        Mockito.when(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .thenThrow(new NoSuchUser(USER_NOT_FOUND_MESSAGE));
        Mockito.when(hashingService.hash(user.getPassword())).thenReturn("pass");
        assertThrows(NoSuchUser.class, () -> service.login(loginUser));
    }

    @Test
    void findClientByUsernameShouldReturnClientIfExist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        ClientServiceModel client = service.findClientByUsername(user.getUsername());
        assertEquals(client.getUsername(), user.getUsername());
    }

    @Test
    void findClientByUsernameShouldThrowIfClient_NOT_Exist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenThrow(NoSuchUser.class);
        assertThrows(NoSuchUser.class, () -> service.findClientByUsername(user.getUsername()));
    }

    @Test
    void findPartnerByUsernameShouldReturnPartnerIfExist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        PartnerServiceModel partner = service.findPartnerByUsername(user.getUsername());
        assertEquals(partner.getUsername(), user.getUsername());
    }

    @Test
    void findPartnerByUsernameShouldThrowIfPartner_NOT_Exist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenThrow(NoSuchUser.class);
        assertThrows(NoSuchUser.class, () -> service.findPartnerByUsername(user.getUsername()));
    }

    @Test
    void findGuideByUsernameShouldReturnGuideIfExists() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        GuideServiceModel guide = service.findGuideByUsername(user.getUsername());
        assertEquals(guide.getUsername(), user.getUsername());
    }

    @Test
    void findGuideByUsernameShouldThrowIfGuide_NOT_Exist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenThrow(NoSuchUser.class);
        assertThrows(NoSuchUser.class, () -> service.findGuideByUsername(user.getUsername()));
    }

    @Test
    void guidesShouldReturnAllGuides() {
        List<User> guides = getDummyUsers(3);
        Role role = new Role("GUIDE");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.findAllByAuthoritiesContaining(role)).thenReturn(guides);
        List<GuideViewModel> models = service.guides();
        assertEquals(guides.size(), models.size());
        assertEquals(guides.get(0).getUsername(), models.get(0).getUsername());
        assertEquals(guides.get(1).getUsername(), models.get(1).getUsername());
        assertEquals(guides.get(2).getUsername(), models.get(2).getUsername());
    }

    @Test
    void partnersShouldReturnAllPartners() {
        List<User> partners = getDummyUsers(3);
        Role role = new Role("PARTNER");
        Mockito.when(roleService.findRoleByName(role.getAuthority())).thenReturn(role);
        Mockito.when(userRepository.findAllByAuthoritiesContaining(role)).thenReturn(partners);
        List<PartnerViewModel> models = service.partners();
        assertEquals(partners.size(), models.size());
        assertEquals(partners.get(0).getUsername(), models.get(0).getUsername());
        assertEquals(partners.get(1).getUsername(), models.get(1).getUsername());
        assertEquals(partners.get(2).getUsername(), models.get(2).getUsername());
    }


    @Test
    void clientAddOfferShouldAddOfferToClientWhenBothExist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        Offer offer = new Offer();
        offer.setName("Offer");
        Mockito.when(offersService.findOfferByName(offer.getName())).thenReturn(offer);
        service.clientAddOffer(user.getUsername(), offer.getName());
        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        Mockito.verify(userRepository).saveAndFlush(captor.capture());
        Client entity = captor.getValue();
        assertEquals(entity.getOffers().get(0), offer);
    }

    @Test
    void clientAddOfferShouldThrowWhenClient_NOT_Exist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenThrow(NoSuchUser.class);
        Offer offer = new Offer();
        offer.setName("Offer");
        assertThrows(NoSuchUser.class, () -> service.clientAddOffer(user.getUsername(), offer.getName()));
    }

    @Test
    void clientAddOfferShouldThrowWhenOffer_NOT_Exist() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        Offer offer = new Offer();
        offer.setName("Offer");
        Mockito.when(offersService.findOfferByName(offer.getName())).thenThrow(NoSuchOffer.class);
        assertThrows(NoSuchOffer.class, () -> service.clientAddOffer(user.getUsername(), offer.getName()));
    }

    @Test
    void findAllUsersShouldReturnAllUsers() {
        List<User> users = getDummyUsers(3);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<UserServiceModel> models = service.findAllUsers();
        assertEquals(users.size(), models.size());
        assertEquals(users.get(0).getUsername(), models.get(0).getUsername());
        assertEquals(users.get(1).getUsername(), models.get(1).getUsername());
        assertEquals(users.get(2).getUsername(), models.get(2).getUsername());
    }

    @Test
    void makeAdmin() {
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
    void deleteAdmin() {
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
                    user.setSector(Sector.CLIENTS);
                    user.setAuthorities(Collections.singleton(new Role("ROLE")));
                    return user;
                })
                .collect(Collectors.toList());
    }
}