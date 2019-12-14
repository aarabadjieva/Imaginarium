package project.imaginarium.service.services;

import project.imaginarium.service.models.offer.AccommodationServiceModel;

public interface OffersService {
    void addOffer(AccommodationServiceModel model, String username);

    AccommodationServiceModel findByName(String name);
}
