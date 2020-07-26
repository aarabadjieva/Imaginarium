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

    @Override
    public String toString() {
        return "\n\n\n\n\n-------------------------------------------------------------------------------------------------------------------------------------" +
        "\n\n\nFROM: " + sender +
                "\nDATE: " + date +
                "\nABOUT: " + about + "\n\n\n"+ text;
    }
}
