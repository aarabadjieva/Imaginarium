package project.imaginarium.web.api.models.article;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ArticleRequestCreateModel {

    private String title;
    private String content;
    private String date;
    private MultipartFile picture;
}
