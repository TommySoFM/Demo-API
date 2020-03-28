package web_demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web_demo.entity.Post;
import web_demo.repository.PostRepository;
import web_demo.service.PostService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
@AutoConfigureTestDatabase
@WithMockUser(username = "TommySo", password = "Abc123!=")
public class PostControllerTest {

    private MockMvc mvc;

    @Autowired
    private PostController postController;

    @MockBean
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.standaloneSetup(this.postController).build();
    }

    @Test
    public void testGetPost() throws Exception {
        List<Post> list = new ArrayList<>();
        Pageable secondPageable =  PageRequest.of(1, 2, Sort.by("creationTimestamp").descending());
        Page<Post> secondPage = new PageImpl<Post>(list, secondPageable,3);

        given(postController.getPost(0)).willReturn(secondPage);

         mvc.perform(get("/post")
                 .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                 .param("page", String.valueOf(0)))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.number", is(1)));
    }

    @Test
    public void testAddNewPost() throws Exception {
        mvc.perform(post("/post")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("postText", "Hello World"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdatePost() throws Exception {
        given(postService.getUsernameFromSession()).willReturn("TommySo");
        given(postService.isPostExist((long) 1)).willReturn(true);
        given(postService.isPostOwner((long) 1, "TommySo")).willReturn(true);
        given(postService.isPostContentUnchanged((long) 1, "Hello World")).willReturn(false);

        mvc.perform(put("/post/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("postText", "Hello World"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testDeletePost() throws Exception {
        given(postService.getUsernameFromSession()).willReturn("TommySo");
        given(postService.isPostExist((long) 1)).willReturn(true);
        given(postService.isPostOwner((long) 1, "TommySo")).willReturn(true);

        mvc.perform(delete("/post/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isAccepted());
    }

    @MockBean
    private OAuth2AuthorizedClientService oauth2Service;

    @MockBean
    private ClientRegistrationRepository oauth2Repository;
}
