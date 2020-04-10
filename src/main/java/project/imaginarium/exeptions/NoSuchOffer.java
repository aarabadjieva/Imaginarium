package project.imaginarium.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such offer")
public class NoSuchOffer extends RuntimeException{

    public NoSuchOffer(String message) {
        super(message);
    }
}
