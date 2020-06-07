package project.imaginarium.service.services;

import project.imaginarium.service.models.MessageServiceModel;

public interface MessageService {

    void send(MessageServiceModel model);

    void delete(MessageServiceModel model);

    void deleteAllFrom(String sender, String recipient);

    void deleteAll(String username);

}
