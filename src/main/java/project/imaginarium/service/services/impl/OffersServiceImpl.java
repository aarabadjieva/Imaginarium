package project.imaginarium.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.imaginarium.data.models.offers.Accommodation;
import project.imaginarium.data.models.offers.Offer;
import project.imaginarium.data.models.offers.TimeTravel;
import project.imaginarium.data.models.offers.Vehicle;
import project.imaginarium.data.models.users.Partner;
import project.imaginarium.data.repositories.OfferRepository;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.service.models.offer.AccommodationServiceModel;
import project.imaginarium.service.models.offer.OfferServiceModel;
import project.imaginarium.service.models.offer.TimeTravelServiceModel;
import project.imaginarium.service.models.offer.VehicleServiceModel;
import project.imaginarium.service.services.OffersService;
import project.imaginarium.exeptions.NoSuchOffer;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.web.models.offer.add.AccommodationAdd;
import project.imaginarium.web.models.offer.add.TimeTravelAdd;
import project.imaginarium.web.models.offer.add.VehicleAdd;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffersServiceImpl implements OffersService {

    private static final String USER_NOT_FOUND_MESSAGE = "User not found";
    private static final String OFFER_NOT_FOUND_MESSAGE = "Offer not found";

    private final ModelMapper mapper;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public OffersServiceImpl(ModelMapper mapper, OfferRepository offerRepository, UserRepository userRepository) {
        this.mapper = mapper;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addAccommodation(AccommodationServiceModel model, String username) {
        Accommodation accommodation = mapper.map(model, Accommodation.class);
        accommodation.setProvider((Partner) userRepository.findByUsername(username).orElseThrow(()->new NoSuchUser(USER_NOT_FOUND_MESSAGE)));
        offerRepository.saveAndFlush(accommodation);
    }

    @Override
    public void addTimeTravel(TimeTravelServiceModel model, String username) {
        TimeTravel timeTravel = mapper.map(model, TimeTravel.class);
        timeTravel.setProvider((Partner) userRepository.findByUsername(username).orElseThrow(()->new NoSuchUser(USER_NOT_FOUND_MESSAGE)));
        offerRepository.saveAndFlush(timeTravel);
    }

    @Override
    public void addVehicle(VehicleServiceModel model, String username) {
        Vehicle vehicle = mapper.map(model, Vehicle.class);
        vehicle.setProvider((Partner) userRepository.findByUsername(username).orElseThrow(()->new NoSuchUser(USER_NOT_FOUND_MESSAGE)));
        offerRepository.saveAndFlush(vehicle);
    }

    @Override
    public void removeOffer(String offerName) {
        Offer offer = offerRepository.findByName(offerName).orElseThrow(()->new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
        offerRepository.delete(offer);
    }

    @Override
    public TimeTravelServiceModel findTimeTravelByName(String name) {
        Offer offer = offerRepository.findByName(name).orElseThrow(()->new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
        return mapper.map(offer, TimeTravelServiceModel.class);
    }

    @Override
    public VehicleServiceModel findVehicleByName(String name) {
        Offer offer = offerRepository.findByName(name).orElseThrow(()->new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
        return mapper.map(offer, VehicleServiceModel.class);
    }

    @Override
    public AccommodationServiceModel findAccommodationByName(String name) {
        Offer offer = offerRepository.findByName(name).orElseThrow(()->new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
        return mapper.map(offer, AccommodationServiceModel.class);
    }

    @Override
    public void updateAccommodation(AccommodationAdd model) {
        Accommodation accommodation = (Accommodation) offerRepository.findByName(model.getName()).orElseThrow(()->new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
        accommodation.setDescription(model.getDescription());
        accommodation.setDays(model.getDays());
        accommodation.setPricePerAdult(model.getPricePerAdult());
        accommodation.setPricePerChildren(model.getPricePerChildren());
        accommodation.setPlanet(model.getPlanet());
        accommodation.setPicture(model.getPicture());
        accommodation.setTags(model.getTags());
        offerRepository.saveAndFlush(accommodation);
    }

    @Override
    public void updateTimeTravel(TimeTravelAdd model) {
        TimeTravel timeTravel = (TimeTravel) offerRepository.findByName(model.getName()).orElseThrow(()->new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
        timeTravel.setAgeRestrictionMin(model.getAgeRestrictionMin());
        timeTravel.setPricePerAdult(model.getPricePerAdult());
        timeTravel.setPricePerChildren(model.getPricePerChildren());
        timeTravel.setYear(model.getYear());
        timeTravel.setDescription(model.getDescription());
        timeTravel.setPlanet(model.getPlanet());
        timeTravel.setPicture(model.getPicture());
        timeTravel.setTags(model.getTags());
        offerRepository.saveAndFlush(timeTravel);
    }

    @Override
    public void updateVehicle(VehicleAdd model) {
        Vehicle vehicle = (Vehicle) offerRepository.findByName(model.getName()).orElseThrow(()->new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
        vehicle.setPricePerDay(model.getPricePerDay());
        vehicle.setDescription(model.getDescription());
        vehicle.setPicture(model.getPicture());
        vehicle.setPlanet(model.getPlanet());
        vehicle.setTags(model.getTags());
        offerRepository.saveAndFlush(vehicle);
    }

    @Override
    public List<OfferServiceModel> findAllOffers() {
        return offerRepository.findAll().stream()
                .map(o->mapper.map(o, OfferServiceModel.class))
                .collect(Collectors.toList());
    }

}
