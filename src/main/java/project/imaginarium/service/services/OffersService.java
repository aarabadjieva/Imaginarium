package project.imaginarium.service.services;

import project.imaginarium.data.models.offers.Offer;
import project.imaginarium.web.view.models.offer.add.AccommodationAdd;
import project.imaginarium.web.view.models.offer.add.TimeTravelAdd;
import project.imaginarium.web.view.models.offer.add.VehicleAdd;
import project.imaginarium.service.models.offer.AccommodationServiceModel;
import project.imaginarium.service.models.offer.OfferServiceModel;
import project.imaginarium.service.models.offer.TimeTravelServiceModel;
import project.imaginarium.service.models.offer.VehicleServiceModel;

import java.io.IOException;
import java.util.List;

public interface OffersService {
    void addAccommodation(AccommodationServiceModel model, String username);

    void addTimeTravel(TimeTravelServiceModel timeTravelServiceModel, String username);

    void addVehicle(VehicleServiceModel vehicleServiceModel, String username);

    void removeOffer(String offerName);

    void updateAccommodation(AccommodationAdd accommodation) throws IOException;

    void updateTimeTravel(TimeTravelAdd timeTravel) throws IOException;

    void updateVehicle(VehicleAdd vehicle) throws IOException;

    List<OfferServiceModel> findAllOffers();

    Offer findOfferByName(String offerName);
}
