package web_demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import web_demo.entity.Post;
import web_demo.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PostService.class)
public class PostServiceTest {

    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Test
    @WithMockUser("TommySo")
    public void testGetUsernameFromSession(){
        assertThat(postService.getUsernameFromSession()).isEqualTo("TommySo");
    }

    @Test
    public void testGetPostByPages() throws Exception{
        Post postFromTommy = new Post("TommySo","Hello World!", LocalDateTime.now());
        Post postFromAmy = new Post("Amy", "Foo", LocalDateTime.now());
        Post postFromWarren = new Post("Warren", "Bar", LocalDateTime.now());
        List<Post> list = new ArrayList<>();
        list.add(postFromTommy);
        list.add(postFromAmy);
        list.add(postFromWarren);

        Pageable firstPageable =  PageRequest.of(0, 2, Sort.by("creationTimestamp").descending());
        Page<Post> firstPage = new PageImpl<Post>(list, firstPageable,3);

        Pageable secondPageable =  PageRequest.of(1, 2, Sort.by("creationTimestamp").descending());
        Page<Post> secondPage = new PageImpl<Post>(list, secondPageable,3);

        when(postRepository.findAll(firstPageable)).thenReturn(firstPage);
        when(postRepository.findAll(secondPageable)).thenReturn(secondPage);

        assertThat(postService.getPostsByPages(0,2).getNumber()).isEqualTo(0);
        assertThat(postService.getPostsByPages(1,2).getNumber()).isEqualTo(1);
        assertThat(postService.getPostsByPages(0,2).getTotalPages()).isEqualTo(2);
        assertThat(postService.getPostsByPages(-10,2).getNumber()).isEqualTo(0);
    }

    @Test
    public void testIsPostContentUnchanged_and_testIsPostOwner() {
        Post post = new Post("TommySo","Hello World!", LocalDateTime.now());
        when(postRepository.findFirstById(Mockito.any(Long.TYPE))).thenReturn(post);

        //Method: isPostContentUnchanged
        assertThat(postService.isPostContentUnchanged((long) 0, "Hello World!")).isTrue();
        assertThat(postService.isPostContentUnchanged((long) 0, "Hello Tommy!")).isFalse();

        //Method: isPostOwner
        assertThat(postService.isPostOwner((long) 0, "TommySo")).isTrue();
        assertThat(postService.isPostOwner((long) 0, "Amy")).isFalse();
    }

    @Test
    public void testIsPostExist() {
        when(postRepository.existsById((long) 0)).thenReturn(true);

        assertThat(postService.isPostExist((long) 0)).isTrue();
        assertThat(postService.isPostExist((long) 1)).isFalse();
    }
}
