package project.imaginarium.service.services.user.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.imaginarium.data.models.offers.Offer;
import project.imaginarium.data.models.users.*;
import project.imaginarium.data.repositories.OfferRepository;
import project.imaginarium.data.repositories.RoleRepository;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.exeptions.NoSuchOffer;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.models.user.*;
import project.imaginarium.service.services.RoleService;
import project.imaginarium.service.services.user.HashingService;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.service.services.user.UserValidationService;
import project.imaginarium.web.models.user.edit.ClientEditModel;
import project.imaginarium.web.models.user.edit.GuideEditModel;
import project.imaginarium.web.models.user.edit.PartnerEditModel;
import project.imaginarium.web.models.user.view.GuideViewModel;
import project.imaginarium.web.models.user.view.PartnerViewModel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User not found";

    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final ModelMapper mapper;
    private final HashingService hashingService;
    private final UserValidationService userValidationService;

    public UserServiceImpl(UserRepository userRepository, OfferRepository offerRepository, RoleRepository roleRepository, RoleService roleService, ModelMapper mapper, HashingService hashingService, UserValidationService userValidationService) {
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.mapper = mapper;
        this.hashingService = hashingService;
        this.userValidationService = userValidationService;
    }

    @Override
    public void saveClient(ClientRegisterServiceModel serviceModel) throws Exception {
        if (userRepository.count()==0) {
            if (roleRepository.findAll().isEmpty()) {
                roleService.seedRolesInDB();
            }
            Role role = roleRepository.findByAuthority("ADMIN");
            serviceModel.setAuthorities(Collections.singleton(role));
        }else {
            Role role = roleRepository.findByAuthority("CLIENT");
            serviceModel.setAuthorities(Collections.singleton(role));
        }
        serviceModel.setPassword(hashingService.hash(serviceModel.getPassword()));
        serviceModel.setConfirmPassword(hashingService.hash(serviceModel.getConfirmPassword()));
        if (!userValidationService.isValidClient(serviceModel)) {
            throw new Exception("Invalid data");
        }
        Client client = mapper.map(serviceModel, Client.class);

        userRepository.saveAndFlush(client);
    }

    public void savePartner(PartnerRegisterServiceModel serviceModel) throws Exception {
        if (userRepository.count()==0) {
            if (roleRepository.findAll().isEmpty()) {
                roleService.seedRolesInDB();
            }
            Role role = roleRepository.findByAuthority("ADMIN");
            serviceModel.setAuthorities(Collections.singleton(role));
        }

        serviceModel.setPassword(hashingService.hash(serviceModel.getPassword()));
        serviceModel.setConfirmPassword(hashingService.hash(serviceModel.getConfirmPassword()));
        switch (serviceModel.getSector().name) {
            case "guides":
                if (!userValidationService.isValidPartner(serviceModel)) {
                    throw new Exception("Invalid data");
                }
                if (serviceModel.getAuthorities().isEmpty()){
                    Role role = roleRepository.findByAuthority("GUIDE");
                    serviceModel.getAuthorities().add(role);
                }
                Guide guide = mapper.map(serviceModel, Guide.class);

                userRepository.saveAndFlush(guide);
                break;
            case "hotels":
            case "vehicles":
            case "timeTravel":
                if (!userValidationService.isValidPartner(serviceModel)) {
                    throw new Exception("Invalid data");
                }
                if (serviceModel.getAuthorities().isEmpty()){
                    Role role = roleRepository.findByAuthority("PARTNER");
                    serviceModel.getAuthorities().add(role);
                }
                Partner partner = mapper.map(serviceModel, Partner.class);

                userRepository.saveAndFlush(partner);
                break;

        }
    }

    @Override
    public UserLoggedServiceModel login(UserServiceLoginModel serviceModel) throws Exception {
        User user = userRepository.findByUsernameAndPassword(serviceModel.getUsername(), hashingService.hash(serviceModel.getPassword())).orElseThrow(() -> new NoSuchUser("Wrong username or password"));
        return mapper.map(user, UserLoggedServiceModel.class);
    }

    @Override
    public ClientServiceModel findClientByUsername(String name) {
        User client = userRepository.findByUsername(name).orElseThrow(() -> new NoSuchUser(USER_NOT_FOUND_MESSAGE));
        return mapper.map(client, ClientServiceModel.class);
    }

    @Override
    public PartnerServiceModel findPartnerByUsername(String name) {
        User partner = userRepository.findByUsername(name).orElseThrow(() -> new NoSuchUser(USER_NOT_FOUND_MESSAGE));
        return mapper.map(partner, PartnerServiceModel.class);
    }

    @Override
    public GuideServiceModel findGuideByUsername(String name) {
        User guide = userRepository.findByUsername(name).orElseThrow(() -> new NoSuchUser(USER_NOT_FOUND_MESSAGE));
        return mapper.map(guide, GuideServiceModel.class);
    }

    @Override
    public List<GuideViewModel> guides() {
        return userRepository.findAllByAuthoritiesContaining(roleRepository.findByAuthority("GUIDE")).stream()
                .map(g -> mapper.map(g, GuideViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PartnerViewModel> partners() {
        return userRepository.findAllByAuthoritiesContaining(roleRepository.findByAuthority("PARTNER")).stream()
                .map(p -> mapper.map(p, PartnerViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateClient(ClientEditModel model) throws Exception {
        Client client = (Client) userRepository.findByUsername(model.getUsername()).orElseThrow(() -> new NoSuchUser(USER_NOT_FOUND_MESSAGE));
        if (!userValidationService.isValidEditClient(client, model)) {
            throw new Exception("Invalid data");
        }
        client.setEmail(model.getEmail());
        if (!model.getPassword().equals("password%")) {
            client.setPassword(hashingService.hash(model.getPassword()));
        }
        client.setCountry(model.getCountry());
        userRepository.saveAndFlush(client);
    }

    @Override
    public void updatePartner(PartnerEditModel model) throws Exception {
        Partner partner = (Partner) userRepository.findByUsername(model.getUsername()).orElseThrow(() -> new NoSuchUser(USER_NOT_FOUND_MESSAGE));
        if (!userValidationService.isValidEditPartner(partner, model)) {
            throw new Exception("Invalid data");
        }
        partner.setEmail(model.getEmail());
        if (!model.getPassword().equals("password%")) {
            partner.setPassword(hashingService.hash(model.getPassword()));
        }
        partner.setName(model.getName());
        partner.setDescription(model.getDescription());
        partner.setLogo(model.getLogo());
        partner.setWebsite(model.getWebsite());
        userRepository.saveAndFlush(partner);
    }

    @Override
    public void updateGuide(GuideEditModel model) throws Exception {
        Guide guide = (Guide) userRepository.findByUsername(model.getUsername()).orElseThrow(() -> new NoSuchUser(USER_NOT_FOUND_MESSAGE));
        if (!userValidationService.isValidEditGuide(guide, model)) {
            throw new Exception("Invalid data");
        }
        guide.setEmail(model.getEmail());
        if (!model.getPassword().equals("password%")) {
            guide.setPassword(hashingService.hash(model.getPassword()));
        }
        guide.setName(model.getName());
        guide.setDescription(model.getDescription());
        guide.setPlanet(model.getPlanet());
        guide.setLogo(model.getLogo());
        guide.setPrice(model.getPrice());
        userRepository.saveAndFlush(guide);
    }

    @Override
    public void clientAddOffer(String user, String offerName) {
        Client client = (Client) userRepository.findByUsername(user).orElseThrow(() -> new NoSuchUser(USER_NOT_FOUND_MESSAGE));
        Offer offer = offerRepository.findByName(offerName).orElseThrow(()-> new NoSuchOffer("Offer not found"));
        client.getOffers().add(offer);
        userRepository.saveAndFlush(client);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
       return userRepository.findAll().stream()
                .map(u->mapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void makeAdmin(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(()->new NoSuchUser(USER_NOT_FOUND_MESSAGE));
        user.getAuthorities().add(roleRepository.findByAuthority("ADMIN"));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteAdmin(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(()->new NoSuchUser(USER_NOT_FOUND_MESSAGE));
        user.getAuthorities().remove(roleRepository.findByAuthority("ADMIN"));
        userRepository.saveAndFlush(user);
    }
}
