package project.imaginarium.web.api.models.article;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleResponseModel {

    private String title;
    private String content;
    private String date;
    private String picture;
}
