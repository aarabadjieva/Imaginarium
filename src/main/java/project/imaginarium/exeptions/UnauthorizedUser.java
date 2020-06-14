package project.imaginarium.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Not authorized")
public class UnauthorizedUser extends RuntimeException{

    public UnauthorizedUser(String message) {
        super(message);
    }

}
