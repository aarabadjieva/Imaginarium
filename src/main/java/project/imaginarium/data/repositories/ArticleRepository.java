package project.imaginarium.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.imaginarium.data.models.Article;

public interface ArticleRepository extends JpaRepository<Article, String> {
    Article findByTitle(String title);
}
