package project.imaginarium.web.view.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.imaginarium.service.models.MailForm;
import project.imaginarium.service.services.MailService;

@Controller
@AllArgsConstructor
public class ContactsController {

    public final static String IMAGINARIUM_CONTACTS_VIEW_NAME = "imaginarium/contacts.html";

    private final MailService mailService;


    @GetMapping("contacts")
    public String getContacts(){
        return IMAGINARIUM_CONTACTS_VIEW_NAME;
    }

    @PostMapping("/contacts")
    public String postContacts(@ModelAttribute MailForm form){
        mailService.sendEmail(form);
        return IMAGINARIUM_CONTACTS_VIEW_NAME;
    }
}
