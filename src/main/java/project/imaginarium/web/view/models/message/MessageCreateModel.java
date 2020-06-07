package project.imaginarium.web.view.models.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageCreateModel {

    private String sender;
    private String about;
    private String text;
    private String date;
    private String recipient;
}
