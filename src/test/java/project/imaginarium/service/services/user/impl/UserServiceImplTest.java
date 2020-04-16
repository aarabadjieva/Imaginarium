package project.imaginarium.service.services.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.users.User;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.service.services.OffersService;
import project.imaginarium.service.services.RoleService;
import project.imaginarium.service.services.user.HashingService;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.service.services.user.UserValidationService;

import java.util.HashSet;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserValidationService userValidationService;
    private RoleService roleService;
    private OffersService offersService;
    private UserService service;
    private User user;

    @BeforeEach
    void setupTest(){
        user = new User("Pesho", "pesho@makarona.com", "password", Sector.CLIENTS, new HashSet<>());
        userRepository = Mockito.mock(UserRepository.class);
        roleService = Mockito.mock(RoleService.class);
        offersService = Mockito.mock(OffersService.class);
        ModelMapper mapper = new ModelMapper();
        HashingService hashingService = new HashingServiceImpl();
        userValidationService = Mockito.mock(UserValidationService.class);
        service = new UserServiceImpl(userRepository, roleService, offersService, mapper, hashingService, userValidationService);
    }

    @Test
    void saveClient() {

    }

    @Test
    void savePartner() {
    }

    @Test
    void login() {
    }

    @Test
    void findClientByUsername() {
    }

    @Test
    void findPartnerByUsername() {
    }

    @Test
    void findGuideByUsername() {
    }

    @Test
    void guides() {
    }

    @Test
    void partners() {
    }

    @Test
    void updateClient() {
    }

    @Test
    void updatePartner() {
    }

    @Test
    void updateGuide() {
    }

    @Test
    void clientAddOffer() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void makeAdmin() {
    }

    @Test
    void deleteAdmin() {
    }
}