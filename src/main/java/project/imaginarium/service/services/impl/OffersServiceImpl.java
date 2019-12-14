package project.imaginarium.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.imaginarium.data.models.Accommodation;
import project.imaginarium.data.models.Partner;
import project.imaginarium.data.repositories.OfferRepository;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.service.models.offer.AccommodationServiceModel;
import project.imaginarium.service.services.OffersService;

@Service
public class OffersServiceImpl implements OffersService {

    private final ModelMapper mapper;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public OffersServiceImpl(ModelMapper mapper, OfferRepository offerRepository, UserRepository userRepository) {
        this.mapper = mapper;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addOffer(AccommodationServiceModel model, String username) {
        Accommodation accommodation = mapper.map(model, Accommodation.class);
        accommodation.setProvider((Partner) userRepository.findByUsername(username));
        offerRepository.saveAndFlush(accommodation);
    }

    @Override
    public AccommodationServiceModel findByName(String name) {
        AccommodationServiceModel accommodation = mapper.map(offerRepository.findByName(name), AccommodationServiceModel.class);
        return accommodation;
    }
}
