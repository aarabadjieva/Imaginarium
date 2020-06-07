package project.imaginarium.service.services.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.imaginarium.data.models.Message;
import project.imaginarium.data.models.users.User;
import project.imaginarium.data.repositories.MessageRepository;
import project.imaginarium.data.repositories.UserRepository;
import project.imaginarium.exeptions.NoSuchMessage;
import project.imaginarium.exeptions.NoSuchUser;
import project.imaginarium.service.models.MessageServiceModel;
import project.imaginarium.service.services.MessageService;

import java.util.List;
import java.util.stream.Collectors;

import static project.imaginarium.exeptions.ExceptionMessage.MESSAGE_NOT_FOUND;
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
    public void delete(String id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new NoSuchMessage(MESSAGE_NOT_FOUND));
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
    public void emptyInbox(String username) {
        List<Message> allMessages = messageRepository.findAllByRecipientUsernameOrderByDateDesc(username);
        for (Message m : allMessages
        ) {
            messageRepository.delete(m);
        }
    }

    @Override
    public List<MessageServiceModel> inbox(String username) {
        return messageRepository.findAllByRecipientUsernameOrderByDateDesc(username)
                .stream()
                .map(m -> mapper.map(m, MessageServiceModel.class))
                .collect(Collectors.toList());
    }



}
