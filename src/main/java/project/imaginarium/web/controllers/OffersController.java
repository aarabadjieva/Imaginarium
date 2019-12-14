package project.imaginarium.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.imaginarium.service.models.offer.AccommodationServiceModel;
import project.imaginarium.service.services.OffersService;
import project.imaginarium.web.models.offer.AccommodationViewModel;
import project.imaginarium.web.models.offer.add.AccommodationAdd;
import project.imaginarium.web.models.offer.add.EventAdd;
import project.imaginarium.web.models.offer.add.VehicleAdd;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/offers")
public class OffersController {

    private final ModelMapper mapper;
    private final OffersService offersService;

    public OffersController(ModelMapper mapper, OffersService offersService) {
        this.mapper = mapper;
        this.offersService = offersService;
    }

    @GetMapping("/add/{offer}")
    public ModelAndView getAddOffer(ModelAndView modelAndView, @PathVariable String offer){
        switch (offer){
            case "hotels":
                modelAndView.setViewName("/offers/add/hotels.html");
                break;
            case "events":
                modelAndView.setViewName("/offers/add/event.html");
                break;
            case "vehicles":
                modelAndView.setViewName("/offers/add/vehicle.html");
                break;
        }
        return modelAndView;
    }

    @PostMapping("/add/{offer}")
    public ModelAndView confirmAddOffer(ModelAndView modelAndView, @PathVariable String offer, HttpSession session,
                                        @ModelAttribute AccommodationAdd accommodation,
                                        @ModelAttribute EventAdd event,
                                        @ModelAttribute VehicleAdd vehicle){
        switch (offer){
            case "hotels":
                AccommodationServiceModel model = mapper.map(accommodation, AccommodationServiceModel.class);
                offersService.addOffer(model, session.getAttribute("username").toString());
                break;
            case "events":
                break;
            case "vehicles":
                break;
        }
        return modelAndView;
    }

    @GetMapping("/info/{sector}/{name}")
    public ModelAndView infoOffer(ModelAndView modelAndView, @PathVariable String sector,
                                  @PathVariable String name, HttpSession session){
        modelAndView.setViewName("/offers/info/" + sector + ".html");
        switch (sector){
            case "hotels":
                AccommodationViewModel accommodation = mapper.map(offersService.findByName(name), AccommodationViewModel.class);
                modelAndView.addObject("offer", accommodation);
                break;
            case "events":
                System.out.println("hello");
                break;
            case "vehicles":
                System.out.println("hellooo");
                break;
        }
        return modelAndView;
    }
}
