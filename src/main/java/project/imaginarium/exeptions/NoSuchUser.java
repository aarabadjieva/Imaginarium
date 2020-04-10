package project.imaginarium.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Wrong username or password")
public class NoSuchUser extends RuntimeException {

    public NoSuchUser(String message) {
        super(message);
    }
}
