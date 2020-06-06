package project.imaginarium.service.services;

import project.imaginarium.service.models.MailForm;

public interface MailService {

    void sendEmail(MailForm form);
}
