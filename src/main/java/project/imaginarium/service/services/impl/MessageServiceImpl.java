package project.imaginarium.service.services.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.imaginarium.data.models.Message;
import project.imaginarium.data.models.users.User;
import project.imaginarium.data.repositories.MessageRepository;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.models.MessageServiceModel;
import project.imaginarium.service.services.MessageService;

import java.util.List;

import static project.imaginarium.exeptions.ExceptionMessage.USER_NOT_FOUND_MESSAGE;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;


    @Override
    public void send(MessageServiceModel model) {
        User recipient = userRepository.findByUsername(model.getRecipient()).orElseThrow(() -> new NoSuchUser(USER_NOT_FOUND_MESSAGE));
        Message message = mapper.map(model, Message.class);
        message.setRecipient(recipient);
        recipient.getInbox().add(message);
        messageRepository.saveAndFlush(message);
    }

    @Override
    public void delete(MessageServiceModel model) {
        Message message = messageRepository.findBySenderAndAboutAndTextAndRecipientUsername(model.getSender(), model.getAbout(),
                model.getText(), model.getRecipient());
        messageRepository.delete(message);
    }

    @Override
    public void deleteAllFrom(String sender, String recipient) {
        List<Message> messagesFromSender = messageRepository.findAllBySenderAndRecipientUsername(sender, recipient);
        for (Message m : messagesFromSender
        ) {
            messageRepository.delete(m);
        }
    }

    @Override
    public void deleteAll(String username) {
        List<Message> allMessages = messageRepository.findAllByRecipientUsername(username);
        for (Message m : allMessages
             ) {
            messageRepository.delete(m);
        }
    }


}
