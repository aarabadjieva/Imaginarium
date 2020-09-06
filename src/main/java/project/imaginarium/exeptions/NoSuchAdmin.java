package project.imaginarium.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such admin")
public class NoSuchAdmin extends RuntimeException{
    public NoSuchAdmin(String message) {
        super(message);
    }
}
