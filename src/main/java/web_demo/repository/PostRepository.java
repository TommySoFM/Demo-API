package web_demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import web_demo.entity.Post;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostRepository extends CrudRepository <Post, Long>{
    Page<Post> getAllByUsernameEquals(String username, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    Post findFirstById(Long id);
}
