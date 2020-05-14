package project.imaginarium.web.controllers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import project.imaginarium.base.ImaginariumApplicationBaseTests;
import project.imaginarium.data.repositories.ArticleRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static project.imaginarium.web.view.controllers.ArticlesController.ARTICLES_CREATE_VIEW_NAME;

@AutoConfigureMockMvc
class ArticlesControllerTest extends ImaginariumApplicationBaseTests {

    @MockBean
    ArticleRepository mockArticleRepo;

    @Autowired
    MockMvc mockMvc;

    @Test
    void createArticle_shouldReturnArticleCreateViewWith200() throws Exception {
        mockMvc.perform(get("/articles/create"))
                .andExpect(status().isOk())
                .andExpect(view().name(ARTICLES_CREATE_VIEW_NAME));
    }

    @Test
    void getEditArticle() {
        //TODO:
    }
}