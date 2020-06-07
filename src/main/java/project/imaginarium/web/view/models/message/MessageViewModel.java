package project.imaginarium.web.view.models.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageViewModel {

    private String id;
    private String sender;
    private String about;
    private boolean isRead;
    private String date;
    private String text;
}
