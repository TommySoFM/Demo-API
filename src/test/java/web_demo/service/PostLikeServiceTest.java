package web_demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import web_demo.repository.PostLikeRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PostLikeService.class)
public class PostLikeServiceTest {

    @Autowired
    private PostLikeService postLikeService;

    @MockBean
    private PostLikeRepository postLikeRepository;

    @Test
    public void testIsPostLiked(){
        when(postLikeRepository.existsByPostIdAndUsername((long) 0, "TommySo")).thenReturn(true);

        assertThat(postLikeService.isPostLiked((long) 0, "TommySo")).isTrue();
    }
}
