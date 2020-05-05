package project.imaginarium.service.services.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.imaginarium.data.models.offers.Accommodation;
import project.imaginarium.data.models.offers.Event;
import project.imaginarium.data.models.offers.Offer;
import project.imaginarium.data.models.offers.Vehicle;
import project.imaginarium.data.models.users.Partner;
import project.imaginarium.data.repositories.OfferRepository;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.exeptions.NoSuchOffer;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.models.offer.AccommodationServiceModel;
import project.imaginarium.service.models.offer.EventServiceModel;
import project.imaginarium.service.models.offer.OfferServiceModel;
import project.imaginarium.service.models.offer.VehicleServiceModel;
import project.imaginarium.service.services.CloudinaryService;
import project.imaginarium.service.services.OffersService;
import project.imaginarium.web.view.models.offer.add.AccommodationAdd;
import project.imaginarium.web.view.models.offer.add.EventAdd;
import project.imaginarium.web.view.models.offer.add.VehicleAdd;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static project.imaginarium.exeptions.ExceptionMessage.OFFER_NOT_FOUND_MESSAGE;
import static project.imaginarium.exeptions.ExceptionMessage.USER_NOT_FOUND_MESSAGE;

@Service
@AllArgsConstructor
public class OffersServiceImpl implements OffersService {


    private final ModelMapper mapper;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;


    @Override
    public void addAccommodation(AccommodationServiceModel model, String username) {
        Accommodation accommodation = mapper.map(model, Accommodation.class);
        accommodation.setProvider((Partner) userRepository.findByUsername(username).orElseThrow(()->new NoSuchUser(USER_NOT_FOUND_MESSAGE)));
        offerRepository.saveAndFlush(accommodation);
    }

    @Override
    public void addEvent(EventServiceModel model, String username) {
        Event event = mapper.map(model, Event.class);
        event.setProvider((Partner) userRepository.findByUsername(username).orElseThrow(()->new NoSuchUser(USER_NOT_FOUND_MESSAGE)));
        offerRepository.saveAndFlush(event);
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
    public void updateAccommodation(AccommodationAdd model) throws IOException {
        Accommodation accommodation = (Accommodation) offerRepository.findByName(model.getName()).orElseThrow(()->new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
        accommodation.setDescription(model.getDescription());
        accommodation.setDays(model.getDays());
        accommodation.setPricePerAdult(model.getPricePerAdult());
        accommodation.setPricePerChildren(model.getPricePerChildren());
        accommodation.setPlanet(model.getPlanet());
        accommodation.setPicture(cloudinaryService.upload(model.getPicture()));
        accommodation.setTags(model.getTags());
        offerRepository.saveAndFlush(accommodation);
    }

    @Override
    public void updateEvent(EventAdd model) throws IOException {
        Event event = (Event) offerRepository.findByName(model.getName()).orElseThrow(()->new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
        event.setAgeRestrictionMin(model.getAgeRestrictionMin());
        event.setPricePerAdult(model.getPricePerAdult());
        event.setPricePerChildren(model.getPricePerChildren());
        event.setYear(model.getYear());
        event.setDescription(model.getDescription());
        event.setPlanet(model.getPlanet());
        event.setPicture(cloudinaryService.upload(model.getPicture()));
        event.setTags(model.getTags());
        offerRepository.saveAndFlush(event);
    }

    @Override
    public void updateVehicle(VehicleAdd model) throws IOException {
        Vehicle vehicle = (Vehicle) offerRepository.findByName(model.getName()).orElseThrow(()->new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
        vehicle.setPricePerDay(model.getPricePerDay());
        vehicle.setDescription(model.getDescription());
        vehicle.setPicture(cloudinaryService.upload(model.getPicture()));
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

    @Override
    public Offer findOfferByName(String offerName) {
        return offerRepository.findByName(offerName).orElseThrow(() -> new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
    }

}
