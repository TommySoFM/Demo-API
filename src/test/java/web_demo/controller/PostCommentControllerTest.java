package web_demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web_demo.service.PostCommentService;
import web_demo.service.PostService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostCommentController.class)
@AutoConfigureTestDatabase
public class PostCommentControllerTest {

    private MockMvc mvc;

    @Autowired
    private PostCommentController postCommentController;

    @MockBean
    private PostCommentService postCommentService;

    @MockBean
    private PostService postService;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.standaloneSetup(this.postCommentController).build();
    }

    @Test
    public void testNewComment() throws Exception {
        given(postService.getUsernameFromSession()).willReturn("TommySo");

        mvc.perform(post("/post/1/comment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                 .param("commentText", "Hello World"))
                .andExpect(status().isAccepted());
    }
}
