package project.imaginarium.service.services.user.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.imaginarium.data.models.*;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.service.models.user.*;
import project.imaginarium.service.services.user.HashingService;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.service.services.user.UserValidationService;
import project.imaginarium.web.models.user.edit.ClientEditModel;
import project.imaginarium.web.models.user.edit.GuideEditModel;
import project.imaginarium.web.models.user.edit.PartnerEditModel;
import project.imaginarium.web.models.user.view.GuideViewModel;
import project.imaginarium.web.models.user.view.PartnerViewModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final HashingService hashingService;
    private final UserValidationService userValidationService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, HashingService hashingService, UserValidationService userValidationService) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.hashingService = hashingService;
        this.userValidationService = userValidationService;
    }

    @Override
    public void saveClient(ClientRegisterServiceModel serviceModel) throws Exception {
        if (userRepository.findAll().isEmpty()) {
            serviceModel.setRole(Role.ADMIN);
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
        if (userRepository.findAll().isEmpty()) {
            serviceModel.setRole(Role.ADMIN);
        }

        serviceModel.setPassword(hashingService.hash(serviceModel.getPassword()));
        serviceModel.setConfirmPassword(hashingService.hash(serviceModel.getConfirmPassword()));
        switch (serviceModel.getRole()) {
            case GUIDE:
                if (!userValidationService.isValidPartner(serviceModel)) {
                    throw new Exception("Invalid data");
                }
                Guide guide = mapper.map(serviceModel, Guide.class);
                userRepository.saveAndFlush(guide);
                break;
            case PARTNER:
                if (!userValidationService.isValidPartner(serviceModel)) {
                    throw new Exception("Invalid data");
                }
                Partner partner = mapper.map(serviceModel, Partner.class);
                userRepository.saveAndFlush(partner);
                break;

        }
    }

    @Override
    public UserLoggedServiceModel login(UserServiceLoginModel serviceModel) throws Exception {
        User user = userRepository.findByUsernameAndPassword(serviceModel.getUsername(), hashingService.hash(serviceModel.getPassword())).orElse(null);
        if (user == null) {
            throw new Exception("Invalid data");
        }
        return mapper.map(user, UserLoggedServiceModel.class);
    }

    @Override
    public ClientServiceModel findClientByUsername(String name) {
        return mapper.map(userRepository.findByUsername(name), ClientServiceModel.class);
    }

    @Override
    public PartnerServiceModel findPartnerByUsername(String name){
        return mapper.map(userRepository.findByUsername(name), PartnerServiceModel.class);
    }

    @Override
    public GuideServiceModel findGuideByUsername(String name) {
        return mapper.map(userRepository.findByUsername(name), GuideServiceModel.class);
    }

    @Override
    public List<GuideViewModel> guides() {
        return userRepository.findAllByRole(Role.GUIDE).stream()
                .map(g -> mapper.map(g, GuideViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PartnerViewModel> partners() {
        return userRepository.findAllByRole(Role.PARTNER).stream()
                .map(p -> mapper.map(p, PartnerViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateClient(ClientEditModel model) throws Exception {
        Client client = (Client) userRepository.findByUsername(model.getUsername());
        if (!userValidationService.isValidEditClient(client, model)){
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
        Partner partner = (Partner) userRepository.findByUsername(model.getUsername());
        if (!userValidationService.isValidEditPartner(partner, model)){
            throw new Exception("Invalid data");
        }
        partner.setEmail(model.getEmail());
        if (!model.getPassword().equals("password%")){
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
        Guide guide = (Guide) userRepository.findByUsername(model.getUsername());
        if (!userValidationService.isValidEditGuide(guide, model)){
            throw new Exception("Invalid data");
        }
        guide.setEmail(model.getEmail());
        if (!model.getPassword().equals("password%")){
            guide.setPassword(hashingService.hash(model.getPassword()));
        }
        guide.setName(model.getName());
        guide.setDescription(model.getDescription());
        guide.setPlanet(model.getPlanet());
        guide.setLogo(model.getLogo());
        guide.setPrice(model.getPrice());
        userRepository.saveAndFlush(guide);
    }
}
