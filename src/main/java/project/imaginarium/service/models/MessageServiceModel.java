package project.imaginarium.service.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageServiceModel {

    private String sender;
    private String about;
    private String text;
    private String date;
    private String recipient;
}
