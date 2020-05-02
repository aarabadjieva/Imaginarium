package project.imaginarium.web.view.models.article;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleCreateModel {

    private String title;
    private String content;
    private String date;
    private String picture;
}
