package project.imaginarium.service.services.impl;

import com.project.imaginarium.ImaginariumApplicationTests;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.offers.Accommodation;
import project.imaginarium.data.models.offers.Offer;
import project.imaginarium.data.models.offers.TimeTravel;
import project.imaginarium.data.models.offers.Vehicle;
import project.imaginarium.data.models.users.Partner;
import project.imaginarium.data.repositories.OfferRepository;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.exeptions.NoSuchOffer;
import project.imaginarium.service.models.offer.AccommodationServiceModel;
import project.imaginarium.service.models.offer.OfferServiceModel;
import project.imaginarium.service.models.offer.TimeTravelServiceModel;
import project.imaginarium.service.models.offer.VehicleServiceModel;
import project.imaginarium.service.services.OffersService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static project.imaginarium.Common.OFFER_NOT_FOUND_MESSAGE;


class OffersServiceImplTest extends ImaginariumApplicationTests {

    @MockBean
    private OfferRepository offerRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private OffersService service;


    @Test
    void shouldAddPartnerCorrectlyForAccommodationAndSaveEntity() {
        AccommodationServiceModel accommodation = new AccommodationServiceModel();
        Partner partner = new Partner();
        partner.setUsername("username");
        Mockito.when(userRepository.findByUsername(partner.getUsername())).thenReturn(Optional.of(partner));
        service.addAccommodation(accommodation, partner.getUsername());
        ArgumentCaptor<Accommodation> captor = ArgumentCaptor.forClass(Accommodation.class);
        Mockito.verify(offerRepository).saveAndFlush(captor.capture());
        Accommodation entity = captor.getValue();
        assertNotNull(entity);
        assertEquals(entity.getProvider(),partner);
    }

    @Test
    void shouldAddPartnerCorrectlyForTimeTravelAndSaveEntity() {
        TimeTravelServiceModel timeTravel = new TimeTravelServiceModel();
        Partner partner = new Partner();
        partner.setUsername("username");
        Mockito.when(userRepository.findByUsername(partner.getUsername())).thenReturn(Optional.of(partner));
        service.addTimeTravel(timeTravel, partner.getUsername());
        ArgumentCaptor<TimeTravel> captor = ArgumentCaptor.forClass(TimeTravel.class);
        Mockito.verify(offerRepository).saveAndFlush(captor.capture());
        TimeTravel entity = captor.getValue();
        assertNotNull(entity);
        assertEquals(entity.getProvider(),partner);
    }

    @Test
    void shouldAddPartnerCorrectlyForVehicleAndSaveEntity() {
        VehicleServiceModel vehicle = new VehicleServiceModel();
        Partner partner = new Partner();
        partner.setUsername("username");
        Mockito.when(userRepository.findByUsername(partner.getUsername())).thenReturn(Optional.of(partner));
        service.addVehicle(vehicle, partner.getUsername());
        ArgumentCaptor<Vehicle> captor = ArgumentCaptor.forClass(Vehicle.class);
        Mockito.verify(offerRepository).saveAndFlush(captor.capture());
        Vehicle entity = captor.getValue();
        assertNotNull(entity);
        assertEquals(entity.getProvider(),partner);
    }

    @Test
    void shouldRemoveOffer() {
        Offer offer = new Offer();
        offer.setName("Name");
        Mockito.when(offerRepository.findByName(offer.getName())).thenReturn(Optional.of(offer));
        service.removeOffer(offer.getName());
        Mockito.verify(offerRepository).delete(offer);
    }

    @Test
    void removeOfferShouldThrowIf_NO_SUCH_OFFER() throws NoSuchOffer {
        String name = "name";
        Mockito.when(offerRepository.findByName(name)).thenThrow(new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
        assertThrows(NoSuchOffer.class, ()->service.removeOffer(name));
    }

    @Test
    void findAllOffersShouldReturnListOfOffers() {
        List<Offer> offers = getDummyOffers(3);
        Mockito.when(offerRepository.findAll()).thenReturn(offers);
        List<OfferServiceModel> models = service.findAllOffers();
        assertEquals(offers.size(), models.size());
        assertEquals(offers.get(0).getName(), models.get(0).getName());
        assertEquals(offers.get(1).getName(), models.get(1).getName());
        assertEquals(offers.get(2).getName(), models.get(2).getName());
    }

    @Test
    void findOfferByNameShouldReturnOfferIfExists() {
        Offer offer = new Offer();
        offer.setName("name");
        Mockito.when(offerRepository.findByName(offer.getName())).thenReturn(Optional.of(offer));
        Offer foundOffer = service.findOfferByName(offer.getName());
        assertEquals(offer,foundOffer);
    }

    @Test
    void findOfferByNameShouldThrowIf_NO_SUCH_OFFER() throws NoSuchOffer {
        String name = "name";
        Mockito.when(offerRepository.findByName(name)).thenThrow(new NoSuchOffer(OFFER_NOT_FOUND_MESSAGE));
        assertThrows(NoSuchOffer.class, ()->service.findOfferByName(name));
    }

    private List<Offer> getDummyOffers(int count) {
        return IntStream.range(0, count)
                .map(x -> x + 1)
                .mapToObj(id -> {
                    Offer offer = new Offer();
                    offer.setId(String.valueOf(id));
                    offer.setName("Offer " + id);
                    offer.setDescription("desc");
                    offer.setPicture("pic");
                    offer.setPlanet(Planet.Asbleg);
                    offer.setProvider(new Partner());
                    return offer;
                })
                .collect(Collectors.toList());
    }
}