package project.imaginarium.service.services;

import project.imaginarium.service.models.MessageServiceModel;

import java.util.List;

public interface MessageService {

    void send(MessageServiceModel model);

    void delete(String id);

    void deleteAllFrom(String sender, String recipient);

    void emptyInbox(String username);

    List<MessageServiceModel> inbox(String username);

}
