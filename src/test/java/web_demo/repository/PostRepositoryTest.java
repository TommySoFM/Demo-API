package web_demo.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import web_demo.entity.Post;
import web_demo.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.ListIterator;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    private Post postFromTommy = new Post("tommy","Hello World", LocalDateTime.now());
    private Post postFromAmy = new Post("amy", "Foo", LocalDateTime.now());

    @Before
    public void init() {
        postRepository.save(postFromTommy);
        postRepository.save(postFromAmy);
    }

    @Test
    public void testFindFirstById() {
        Long first = Long.valueOf(1);
        Long second = Long.valueOf(2);

        Post firstPost = postRepository.findFirstById(first);
        assertEquals("tommy", firstPost.getUsername());
        assertEquals("Hello World", firstPost.getPostText());

        Post secondPost = postRepository.findFirstById(second);
        assertEquals("amy", secondPost.getUsername());
        assertEquals("Foo", secondPost.getPostText());
    }

    @Test
    public void testFindAll() {
        Pageable sortFirstPage = PageRequest.of(0, 3, Sort.by("creationTimestamp").descending());

        Page<Post> postPage = postRepository.findAll(sortFirstPage);
        assertEquals(2, postPage.getTotalElements());
        assertEquals(1, postPage.getTotalPages());

        ListIterator<Post> posts = postPage.getContent().listIterator();

        while(posts.hasNext()) {
            Post post = posts.next();
            if (post.getId() == 1) {
                assertEquals("tommy", post.getUsername());
            } else if (post.getId() == 2) {
                assertEquals("amy", post.getUsername());
            }
        }
    }
}
