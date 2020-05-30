package project.imaginarium.service.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ArticleServiceModel {

    private String title;
    private String content;
    private String date;
    private MultipartFile picture;
}
