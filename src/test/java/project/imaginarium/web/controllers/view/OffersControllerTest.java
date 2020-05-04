package project.imaginarium.web.controllers.view;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import project.imaginarium.base.ImaginariumApplicationBaseTests;
import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.offers.Offer;
import project.imaginarium.data.models.users.Partner;
import project.imaginarium.data.models.users.Role;
import project.imaginarium.data.models.users.User;
import project.imaginarium.data.repositories.OfferRepository;
import project.imaginarium.data.repositories.RoleRepository;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.exeptions.NoSuchOffer;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.services.CloudinaryService;
import project.imaginarium.web.view.controllers.OffersController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static project.imaginarium.web.view.controllers.OffersController.*;

@AutoConfigureMockMvc
class OffersControllerTest extends ImaginariumApplicationBaseTests {

    @MockBean
    UserRepository mockUserRepo;

    @MockBean
    OfferRepository mockOfferRepo;

    @MockBean
    RoleRepository mockRoleRepo;

    @MockBean
    CloudinaryService mockCloudinaryService;

    @Autowired
    MockMvc mockMvc;


    @Test
    void addOffer_shouldReturnAddHotelsWhenOfferIsHotelWith200() throws Exception {
        String partner = "someone";
        String offer = "hotels";
        mockMvc.perform(get("/offers/" + partner + "/add/" + offer))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("accommodation"))
                .andExpect(view().name(OFFERS_ADD_HOTELS_VIEW_NAME));
    }

    @Test
    void addOffer_shouldReturnAddTimeTravelWhenOfferIsTimeTravelWith200() throws Exception {
        String partner = "someone";
        String offer = "timeTravel";
        mockMvc.perform(get("/offers/" + partner + "/add/" + offer))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("timeTravel"))
                .andExpect(view().name(OFFERS_ADD_TIME_TRAVEL_VIEW_NAME));
    }

    @Test
    void addOffer_shouldReturnAddVehiclesWhenOfferIsVehicleWith200() throws Exception {
        String partner = "someone";
        String offer = "vehicles";
        mockMvc.perform(get("/offers/" + partner + "/add/" + offer))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vehicle"))
                .andExpect(view().name(OffersController.OFFERS_ADD_VEHICLES_VIEW_NAME));
    }

    @Test
    void addHotel_shouldRedirectToOfferInfoPageWhenOfferHas_NO_ErrorsAndPartnerExistsWith302() throws Exception {
        Partner partner = new Partner();
        partner.setUsername("name");
        Mockito.when(mockUserRepo.findByUsername(partner.getUsername())).thenReturn(of(partner));
        mockMvc.perform(post("/offers/" + partner.getUsername() + "/add/hotels")
                .param("name", "name")
                .param("description", "description")
                .param("days", "3")
                .param("planet", String.valueOf(Planet.Bartledan))
                .param("pricePerAdult", "333")
                .param("pricePerChildren", "333"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/offers/info/hotels/name")); //{name} is accommodation name
    }

    @Test
    void addHotel_shouldReturnAddHotelViewWhenOffer_HAS_Errors() throws Exception {
        String partner = "name";
        mockMvc.perform(post("/offers/" + partner + "/add/hotels")
                .param("name", "")) //name has errors
                .andExpect(view().name(OFFERS_ADD_HOTELS_VIEW_NAME))
                .andExpect(model().attributeHasErrors("accommodation"));
    }

    @Test
    void addHotel_shouldThrowWhenPartner_DO_NOT_ExistsWith404() throws Exception {
        String partner = "name";
        Mockito.when(mockUserRepo.findByUsername(partner)).thenThrow(NoSuchUser.class);
        mockMvc.perform(post("/offers/" + partner + "/add/hotels")
                .param("name", "name")
                .param("description", "description")
                .param("days", "3")
                .param("planet", String.valueOf(Planet.Bartledan))
                .param("pricePerAdult", "333")
                .param("pricePerChildren", "333"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));

    }

    @Test
    void addTimeTravel_shouldRedirectToOfferInfoPageWhenOfferHas_NO_ErrorsAndPartnerExistsWith302() throws Exception {
        Partner partner = new Partner();
        partner.setUsername("name");
        Mockito.when(mockUserRepo.findByUsername(partner.getUsername())).thenReturn(of(partner));
        mockMvc.perform(post("/offers/" + partner.getUsername() + "/add/timeTravel")
                .param("name", "name")
                .param("description", "description")
                .param("planet", String.valueOf(Planet.Bartledan))
                .param("pricePerAdult", "333")
                .param("pricePerChildren", "333")
                .param("year", "333")
                .param("ageRestrictionMin", "333"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/offers/info/timeTravel/name")); //{name} is timeTravel name
    }

    @Test
    void addTimeTravel_shouldReturnAddTimeTravelViewWhenOffer_HAS_Errors() throws Exception {
        String partner = "name";
        mockMvc.perform(post("/offers/" + partner + "/add/timeTravel")
                .param("name", "")) //name has errors
                .andExpect(view().name(OFFERS_ADD_TIME_TRAVEL_VIEW_NAME))
                .andExpect(model().attributeHasErrors("timeTravel"));
    }

    @Test
    void addTimeTravel_shouldThrowWhenPartner_DO_NOT_ExistsWith404() throws Exception {
        String partner = "name";
        Mockito.when(mockUserRepo.findByUsername(partner)).thenThrow(NoSuchUser.class);
        mockMvc.perform(post("/offers/" + partner + "/add/timeTravel")
                .param("name", "name")
                .param("description", "description")
                .param("planet", String.valueOf(Planet.Bartledan))
                .param("pricePerAdult", "333")
                .param("pricePerChildren", "333")
                .param("year", "333")
                .param("ageRestrictionMin", "333"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));

    }

    @Test
    void addVehicle_shouldRedirectToOfferInfoPageWhenOfferHas_NO_ErrorsAndPartnerExistsWith302() throws Exception {
        Partner partner = new Partner();
        partner.setUsername("name");
        Mockito.when(mockUserRepo.findByUsername(partner.getUsername())).thenReturn(of(partner));
        mockMvc.perform(post("/offers/" + partner.getUsername() + "/add/vehicles")
                .param("name", "name")
                .param("description", "description")
                .param("planet", String.valueOf(Planet.Bartledan))
                .param("pricePerDay", "333"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/offers/info/vehicles/name")); //{name} is vehicle name
    }

    @Test
    void addVehicle_shouldReturnAddVehicleViewWhenOffer_HAS_Errors() throws Exception {
        String partner = "name";
        mockMvc.perform(post("/offers/" + partner + "/add/vehicles")
                .param("name", "")) //name has errors
                .andExpect(view().name(OffersController.OFFERS_ADD_VEHICLES_VIEW_NAME))
                .andExpect(model().attributeHasErrors("vehicle"));
    }

    @Test
    void addVehicle_shouldThrowWhenPartner_DO_NOT_ExistsWith404() throws Exception {
        String partner = "name";
        Mockito.when(mockUserRepo.findByUsername(partner)).thenThrow(NoSuchUser.class);
        mockMvc.perform(post("/offers/" + partner + "/add/vehicles")
                .param("name", "name")
                .param("description", "description")
                .param("planet", String.valueOf(Planet.Bartledan))
                .param("pricePerDay", "333"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));

    }

    @Test
    void infoOffer_shouldReturnHotelOfferInfoWhenSectorIsHotelsAndOfferExists() throws Exception {
        MockHttpSession session = new MockHttpSession();
        Partner provider = new Partner();
        provider.setUsername("someone");
        Offer offer = new Offer();
        offer.setName("name");
        offer.setSector(Sector.HOTELS);
        offer.setProvider(provider);
        session.setAttribute("user", provider);
        Mockito.when(mockOfferRepo.findByName(offer.getName())).thenReturn(of(offer));
        mockMvc.perform(get("/offers/info/"+offer.getSector().getName()+"/"+offer.getName())
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("offer"))
                .andExpect(view().name(OFFERS_INFO_HOTELS_VIEW_NAME));
    }

    @Test
    void infoOffer_shouldReturnTimeTravelOfferInfoWhenSectorIsHotelsAndOfferExists() throws Exception {
        MockHttpSession session = new MockHttpSession();
        Partner provider = new Partner();
        provider.setUsername("someone");
        Offer offer = new Offer();
        offer.setName("name");
        offer.setSector(Sector.TIME_TRAVEL);
        offer.setProvider(provider);
        session.setAttribute("user", provider);
        Mockito.when(mockOfferRepo.findByName(offer.getName())).thenReturn(of(offer));
        mockMvc.perform(get("/offers/info/"+offer.getSector().getName()+"/"+offer.getName())
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("offer"))
                .andExpect(view().name(OFFERS_INFO_TIME_TRAVEL_VIEW_NAME));
    }

    @Test
    void infoOffer_shouldReturnVehiclesOfferInfoWhenSectorIsHotelsAndOfferExists() throws Exception {
        MockHttpSession session = new MockHttpSession();
        Partner provider = new Partner();
        provider.setUsername("someone");
        Offer offer = new Offer();
        offer.setName("name");
        offer.setSector(Sector.VEHICLES);
        offer.setProvider(provider);
        session.setAttribute("user", provider);
        Mockito.when(mockOfferRepo.findByName(offer.getName())).thenReturn(of(offer));
        mockMvc.perform(get("/offers/info/"+offer.getSector().getName()+"/"+offer.getName())
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("offer"))
                .andExpect(view().name(OFFERS_INFO_VEHICLES_VIEW_NAME));
    }

    @Test
    void infoOffer_shouldThrowIfOffer_DO_NOT_Exists() throws Exception {
        Offer offer = new Offer();
        offer.setName("name");
        offer.setSector(Sector.HOTELS);
        Mockito.when(mockOfferRepo.findByName(offer.getName())).thenThrow(NoSuchOffer.class);
        mockMvc.perform(get("/offers/info/"+offer.getSector().getName().toLowerCase()+"/"+offer.getName()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));
    }

    @Test
    void deleteOffer_shouldRedirectToProviderProfileIfOfferExists() throws Exception {
        Partner partner = new Partner();
        partner.setUsername("someone");
        Offer offer = new Offer();
        offer.setName("name");
        offer.setProvider(partner);
        Mockito.when(mockOfferRepo.findByName(offer.getName())).thenReturn(Optional.of(offer));
        mockMvc.perform(post("/offers/"+partner.getUsername()+"/delete/"+offer.getName()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/profile/partner/" + partner.getUsername()));
    }

    @Test
    void deleteOffer_shouldThrowIfOffer_DO_NOT_Exists() throws Exception {
        Partner partner = new Partner();
        partner.setUsername("someone");
        Offer offer = new Offer();
        offer.setName("name");
        offer.setProvider(partner);
        Mockito.when(mockOfferRepo.findByName(offer.getName())).thenThrow(NoSuchOffer.class);
        mockMvc.perform(post("/offers/"+partner.getUsername()+"/delete/"+offer.getName()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-custom.html"));
    }

    @Test
    void getAllOffersAndAllGuides_shouldReturnAllOffersView() throws Exception {
        List<Offer> allOffers = new ArrayList<>();
        List<User> allGuides = new ArrayList<>();
        Role role = new Role("GUIDE");
        Mockito.when(mockOfferRepo.findAll()).thenReturn(allOffers);
        Mockito.when(mockRoleRepo.findByAuthority(role.getAuthority())).thenReturn(Optional.of(role));
        Mockito.when(mockUserRepo.findAllByAuthoritiesContaining(role)).thenReturn(allGuides);
        mockMvc.perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("offers", "guides"))
                .andExpect(view().name(ALL_OFFERS_VIEW_NAME));
    }
}