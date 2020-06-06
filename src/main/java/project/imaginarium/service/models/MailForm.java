package project.imaginarium.service.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailForm {

    private String email;
    private String subject;
    private String message;
}
