package project.imaginarium.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "articles")
public class Article extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, length = 13000)
    private String content;

    @Column(nullable = false)
    private String picture;
}
