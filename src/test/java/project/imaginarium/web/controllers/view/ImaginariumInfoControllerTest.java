package project.imaginarium.web.controllers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import project.imaginarium.base.ImaginariumApplicationBaseTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static project.imaginarium.web.view.controllers.ImaginariumInfoController.*;

@AutoConfigureMockMvc
class ImaginariumInfoControllerTest extends ImaginariumApplicationBaseTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getHome_shouldReturnHomeViewWith200() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name(IMAGINARIUM_HOME_VIEW_NAME));
    }

    @Test
    void getAboutInfo_shouldReturnAboutViewWith200() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name(IMAGINARIUM_ABOUT_VIEW_NAME));
    }

   // @Test
   // void getContacts_shouldReturnContactsViewWith200() throws Exception {
   //     mockMvc.perform(get("/contacts"))
   //             .andExpect(status().isOk())
   //             .andExpect(view().name(IMAGINARIUM_CONTACTS_VIEW_NAME));
   // }

    @Test
    void getBlog_shouldReturnBlogViewWith200() throws Exception {
        mockMvc.perform(get("/blog"))
                .andExpect(status().isOk())
                .andExpect(view().name(IMAGINARIUM_BLOG_VIEW_NAME));
    }
}