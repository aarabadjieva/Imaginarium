package project.imaginarium.service.services;

import project.imaginarium.data.models.offers.Offer;
import project.imaginarium.data.models.offers.Tag;
import project.imaginarium.service.models.offer.AccommodationServiceModel;
import project.imaginarium.service.models.offer.EventServiceModel;
import project.imaginarium.service.models.offer.OfferServiceModel;
import project.imaginarium.service.models.offer.VehicleServiceModel;
import project.imaginarium.web.view.models.offer.add.AccommodationAdd;
import project.imaginarium.web.view.models.offer.add.EventAdd;
import project.imaginarium.web.view.models.offer.add.VehicleAdd;

import java.io.IOException;
import java.util.List;

public interface OffersService {
    void addAccommodation(AccommodationServiceModel model, String username);

    void addEvent(EventServiceModel eventServiceModel, String username);

    void addVehicle(VehicleServiceModel vehicleServiceModel, String username);

    void removeOffer(String offerName);

    void updateAccommodation(AccommodationAdd accommodation) throws IOException;

    void updateEvent(EventAdd timeTravel) throws IOException;

    void updateVehicle(VehicleAdd vehicle) throws IOException;

    List<OfferServiceModel> findAllOffers();

    Offer findOfferByName(String offerName);

    List<VehicleServiceModel> findAllVehicles();

    List<AccommodationServiceModel> findAllAccommodations();

    List<EventServiceModel> findAllEvents();

    List <OfferServiceModel> findAllByTag(Tag tag);
}
