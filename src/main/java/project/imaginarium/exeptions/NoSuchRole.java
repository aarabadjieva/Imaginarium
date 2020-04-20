package project.imaginarium.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such authority found")
public class NoSuchRole extends RuntimeException {

    public NoSuchRole(String message) {
        super(message);
    }

}
