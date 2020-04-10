package project.imaginarium.service.services;

import project.imaginarium.service.models.offer.AccommodationServiceModel;
import project.imaginarium.service.models.offer.OfferServiceModel;
import project.imaginarium.service.models.offer.TimeTravelServiceModel;
import project.imaginarium.service.models.offer.VehicleServiceModel;
import project.imaginarium.web.models.offer.add.AccommodationAdd;
import project.imaginarium.web.models.offer.add.TimeTravelAdd;
import project.imaginarium.web.models.offer.add.VehicleAdd;

import java.util.List;

public interface OffersService {
    void addAccommodation(AccommodationServiceModel model, String username);

    AccommodationServiceModel findAccommodationByName(String name);

    void addTimeTravel(TimeTravelServiceModel timeTravelServiceModel, String username);

    void addVehicle(VehicleServiceModel vehicleServiceModel, String username);

    void removeOffer(String offerName);

    TimeTravelServiceModel findTimeTravelByName(String name);

    VehicleServiceModel findVehicleByName(String name);

    void updateAccommodation(AccommodationAdd accommodation);

    void updateTimeTravel(TimeTravelAdd timeTravel);

    void updateVehicle(VehicleAdd vehicle);

    List<OfferServiceModel> findAllOffers();
}
