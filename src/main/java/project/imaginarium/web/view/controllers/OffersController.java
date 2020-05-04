package project.imaginarium.web.view.controllers;

import org.springframework.http.HttpStatus;
import project.imaginarium.exeptions.NoSuchOffer;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.services.CloudinaryService;
import project.imaginarium.web.api.models.user.response.GuideResponseModel;
import project.imaginarium.web.view.models.offer.add.AccommodationAdd;
import project.imaginarium.web.view.models.offer.add.TimeTravelAdd;
import project.imaginarium.web.view.models.offer.add.VehicleAdd;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.service.models.offer.AccommodationServiceModel;
import project.imaginarium.service.models.offer.TimeTravelServiceModel;
import project.imaginarium.service.models.offer.VehicleServiceModel;
import project.imaginarium.service.services.OffersService;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.web.view.models.offer.view.AccommodationViewModel;
import project.imaginarium.web.view.models.offer.view.OfferShortViewModel;
import project.imaginarium.web.view.models.offer.view.TimeTravelViewModel;
import project.imaginarium.web.view.models.offer.view.VehicleViewModel;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/offers")
public class OffersController {

    public final static String OFFERS_ADD_HOTELS_VIEW_NAME = "/offers/add/hotels.html";
    public final static String OFFERS_ADD_TIME_TRAVEL_VIEW_NAME = "/offers/add/timetravel.html";
    public final static String OFFERS_ADD_VEHICLES_VIEW_NAME = "/offers/add/vehicles.html";
    public final static String OFFERS_INFO_HOTELS_VIEW_NAME = "/offers/info/hotels.html";
    public final static String OFFERS_INFO_TIME_TRAVEL_VIEW_NAME = "/offers/info/timetravel.html";
    public final static String OFFERS_INFO_VEHICLES_VIEW_NAME = "/offers/info/vehicles.html";
    public final static String OFFERS_EDIT_HOTELS_VIEW_NAME = "/offers/edit/hotels.html";
    public final static String OFFERS_EDIT_TIME_TRAVEL_VIEW_NAME = "/offers/edit/timetravel.html";
    public final static String OFFERS_EDIT_VEHICLES_VIEW_NAME = "/offers/edit/vehicles.html";
    public final static String ALL_OFFERS_VIEW_NAME = "/offers/all-offers.html";

    private final ModelMapper mapper;
    private final OffersService offersService;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    @ModelAttribute("accommodation")
    public AccommodationAdd accommodationAdd() {
        return new AccommodationAdd();
    }

    @ModelAttribute("timeTravel")
    public TimeTravelAdd timeTravelAdd() {
        return new TimeTravelAdd();
    }

    @ModelAttribute("vehicle")
    public VehicleAdd vehicleAdd() {
        return new VehicleAdd();
    }

    @GetMapping("/{partner}/add/{offer}")
    public ModelAndView addOffer(ModelAndView modelAndView, @PathVariable String offer, @PathVariable String partner) {
        switch (offer) {
            case "hotels":
                modelAndView.setViewName(OFFERS_ADD_HOTELS_VIEW_NAME);
                modelAndView.addObject(accommodationAdd());
                break;
            case "timeTravel":
                modelAndView.clear();
                modelAndView.setViewName(OFFERS_ADD_TIME_TRAVEL_VIEW_NAME);
                modelAndView.addObject(timeTravelAdd());
                break;
            case "vehicles":
                modelAndView.setViewName(OFFERS_ADD_VEHICLES_VIEW_NAME);
                modelAndView.addObject(vehicleAdd());
                break;
        }
        return modelAndView;
    }


    @PostMapping("/{partner}/add/hotels")
    public ModelAndView addHotel(ModelAndView modelAndView, @PathVariable String partner,
                                 @Valid
                                 @ModelAttribute("accommodation") AccommodationAdd accommodation,
                                 BindingResult accommodationResult) throws IOException {
        if (accommodationResult.hasErrors()) {
            modelAndView.setViewName(OFFERS_ADD_HOTELS_VIEW_NAME);
            return modelAndView;
        }
        AccommodationServiceModel accommodationServiceModel = mapper.map(accommodation, AccommodationServiceModel.class);
        accommodationServiceModel.setPicture(cloudinaryService.upload(accommodation.getPicture()));
        offersService.addAccommodation(accommodationServiceModel, partner);
        modelAndView.setViewName("redirect:/offers/info/hotels/" + accommodation.getName());
        return modelAndView;
    }

    @PostMapping("/{partner}/add/timeTravel")
    public ModelAndView addTimeTravel(ModelAndView modelAndView, @PathVariable String partner,
                                      @Valid
                                      @ModelAttribute("timeTravel") TimeTravelAdd timeTravel,
                                      BindingResult timeTravelResult) throws IOException {
        if (timeTravelResult.hasErrors()) {
            modelAndView.setViewName(OFFERS_ADD_TIME_TRAVEL_VIEW_NAME);
            return modelAndView;
        }
        TimeTravelServiceModel timeTravelServiceModel = mapper.map(timeTravel, TimeTravelServiceModel.class);
        timeTravelServiceModel.setPicture(cloudinaryService.upload(timeTravel.getPicture()));
        offersService.addTimeTravel(timeTravelServiceModel, partner);
        modelAndView.setViewName("redirect:/offers/info/timeTravel/" + timeTravel.getName());

        return modelAndView;
    }

    @PostMapping("/{partner}/add/vehicles")
    public ModelAndView addVehicle(ModelAndView modelAndView, @PathVariable String partner,
                                   @Valid
                                   @ModelAttribute("vehicle") VehicleAdd vehicle,
                                   BindingResult vehicleResult) throws IOException {
        if (vehicleResult.hasErrors()) {
            modelAndView.setViewName(OFFERS_ADD_VEHICLES_VIEW_NAME);
            return modelAndView;
        }
        VehicleServiceModel vehicleServiceModel = mapper.map(vehicle, VehicleServiceModel.class);
        vehicleServiceModel.setPicture(cloudinaryService.upload(vehicle.getPicture()));
        offersService.addVehicle(vehicleServiceModel, partner);
        modelAndView.setViewName("redirect:/offers/info/vehicles/" + vehicle.getName());
        return modelAndView;
    }

    @GetMapping("/info/{sector}/{name}")
    public ModelAndView infoOffer(ModelAndView modelAndView, @PathVariable String sector,
                                  @PathVariable String name) {

        switch (sector) {
            case "hotels":
                modelAndView.setViewName(OFFERS_INFO_HOTELS_VIEW_NAME);
                AccommodationViewModel accommodation = mapper.map(offersService.findOfferByName(name), AccommodationViewModel.class);
                modelAndView.addObject("offer", accommodation);
                break;
            case "timeTravel":
                modelAndView.setViewName(OFFERS_INFO_TIME_TRAVEL_VIEW_NAME);
                TimeTravelViewModel timeTravel = mapper.map(offersService.findOfferByName(name), TimeTravelViewModel.class);
                modelAndView.addObject("offer", timeTravel);
                break;
            case "vehicles":
                modelAndView.setViewName(OFFERS_INFO_VEHICLES_VIEW_NAME);
                VehicleViewModel vehicle = mapper.map(offersService.findOfferByName(name), VehicleViewModel.class);
                modelAndView.addObject("offer", vehicle);
                break;
        }
        return modelAndView;
    }


    @GetMapping("/{partner}/edit/{sector}/{name}")
    public ModelAndView editOffer(ModelAndView modelAndView,
                                  @PathVariable String partner,
                                  @PathVariable String sector,
                                  @PathVariable String name) {
        switch (sector) {
            case "hotels":
                modelAndView.setViewName(OFFERS_EDIT_HOTELS_VIEW_NAME);
                AccommodationViewModel accommodation = mapper.map(offersService.findOfferByName(name), AccommodationViewModel.class);
                modelAndView.addObject("accommodation", accommodation);
                break;
            case "timeTravel":
                modelAndView.setViewName(OFFERS_EDIT_TIME_TRAVEL_VIEW_NAME);
                TimeTravelViewModel timeTravel = mapper.map(offersService.findOfferByName(name), TimeTravelViewModel.class);
                modelAndView.addObject("timeTravel", timeTravel);
                break;
            case "vehicles":
                modelAndView.setViewName(OFFERS_EDIT_VEHICLES_VIEW_NAME);
                VehicleViewModel vehicle = mapper.map(offersService.findOfferByName(name), VehicleViewModel.class);
                modelAndView.addObject("vehicle", vehicle);
                break;
        }
        return modelAndView;
    }

    @PostMapping("/{partner}/edit/hotels/{name}")
    public ModelAndView editHotel(ModelAndView modelAndView,
                                  @PathVariable String name,
                                  @PathVariable String partner,
                                  @Valid
                                  @ModelAttribute("accommodation") AccommodationAdd accommodation,
                                  BindingResult accommodationResult) throws IOException {
        accommodation.setName(name);
        if (accommodationResult.hasErrors()) {
            modelAndView.setViewName(OFFERS_EDIT_HOTELS_VIEW_NAME);
            return modelAndView;
        }
        offersService.updateAccommodation(accommodation);
        modelAndView.setViewName("redirect:/offers/info/hotels/" + name);
        modelAndView.addObject(accommodation);
        return modelAndView;
    }

    @PostMapping("/{partner}/edit/timeTravel/{name}")
    public ModelAndView editTimeTravel(ModelAndView modelAndView,
                                       @PathVariable String name,
                                       @PathVariable String partner,
                                       @Valid
                                  @ModelAttribute("timeTravel") TimeTravelAdd timeTravel,
                                       BindingResult timeTravelResult) throws IOException {
        timeTravel.setName(name);
        if (timeTravelResult.hasErrors()) {
            modelAndView.setViewName(OFFERS_EDIT_TIME_TRAVEL_VIEW_NAME);
            return modelAndView;
        }
        offersService.updateTimeTravel(timeTravel);
        modelAndView.setViewName("redirect:/offers/info/timeTravel/" + name);
        modelAndView.addObject(timeTravel);
        return modelAndView;
    }

    @PostMapping("/{partner}/edit/vehicles/{name}")
    public ModelAndView editVehicle(ModelAndView modelAndView,
                                    @PathVariable String partner,
                                    @PathVariable String name,
                                    @Valid
                                  @ModelAttribute("vehicle") VehicleAdd vehicle,
                                    BindingResult vehicleResult) throws IOException {
        vehicle.setName(name);
        if (vehicleResult.hasErrors()) {
            modelAndView.setViewName(OFFERS_EDIT_VEHICLES_VIEW_NAME);
            return modelAndView;
        }
        offersService.updateVehicle(vehicle);
        modelAndView.setViewName("redirect:/offers/info/vehicles/" + name);
        modelAndView.addObject(vehicle);
        return modelAndView;
    }

    @PostMapping("/{partner}/delete/{offerName}")
    public ModelAndView deleteOffer(@PathVariable String partner, @PathVariable String offerName) {
        offersService.removeOffer(offerName);
        return new ModelAndView("redirect:/profile/partner/" + partner);
    }

    @GetMapping("")
    public ModelAndView getAllOffersAndAllGuides(ModelAndView modelAndView){
        List<OfferShortViewModel> allOffers = offersService.findAllOffers()
                .stream()
                .map(o->mapper.map(o, OfferShortViewModel.class))
                .collect(Collectors.toList());
        List<GuideResponseModel> allGuides = userService.guides();
        modelAndView.addObject("offers", allOffers);
        modelAndView.addObject("guides", allGuides);
        modelAndView.setViewName(ALL_OFFERS_VIEW_NAME);
        return modelAndView;
    }

    @ExceptionHandler({NoSuchUser.class, NoSuchOffer.class})
    public ModelAndView handleException(Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error-custom.html");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
