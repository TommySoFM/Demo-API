package web_demo.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web_demo.service.PostLikeService;
import web_demo.service.PostService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PostLikeController.class)
@AutoConfigureTestDatabase
@WithMockUser(username = "TommySo", password = "Abc123!=")
public class PostLikeControllerTest {

    private MockMvc mvc;

    @Autowired
    private PostLikeController postLikeController;

    @MockBean
    private PostLikeService postLikeService;

    @MockBean
    private PostService postService;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.standaloneSetup(this.postLikeController).build();
    }

    @Test
    public void testIsLiked() throws Exception {
        given(postService.getUsernameFromSession()).willReturn("TommySo");
        given(postLikeService.isPostLiked((long) 1, "TommySo")).willReturn(true);

        mvc.perform(post("/post/1/isLiked"))
                .andExpect(status().isAccepted())
                .andExpect(content().string("true"));
    }

    @Test
    public void testToggleLike() throws Exception {
        given(postService.getUsernameFromSession()).willReturn("TommySo");
        given(postLikeService.isPostLiked((long) 1, "TommySo")).willReturn(true);
        given(postLikeService.isPostLiked((long) 2, "TommySo")).willReturn(false);

        mvc.perform(post("/post/1/like"))
                .andExpect(status().isAccepted())
                .andExpect(content().string("unliked"));

        mvc.perform(post("/post/2/like"))
                .andExpect(status().isAccepted())
                .andExpect(content().string("liked"));
    }

}
