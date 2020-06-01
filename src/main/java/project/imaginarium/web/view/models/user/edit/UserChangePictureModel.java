package project.imaginarium.web.view.models.user.edit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserChangePictureModel {

    private MultipartFile picture;
}
