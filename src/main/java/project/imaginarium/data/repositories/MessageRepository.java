package project.imaginarium.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.imaginarium.data.models.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {

    Message findBySenderAndAboutAndTextAndRecipientUsername(String sender, String about, String text, String recipient);
    List<Message> findAllBySenderAndRecipientUsername(String sender, String recipient);
    List<Message> findAllByRecipientUsernameOrderByDateDesc(String username);
}
