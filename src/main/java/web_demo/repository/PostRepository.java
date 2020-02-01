package web_demo.repository;

import org.springframework.data.repository.CrudRepository;
import web_demo.entity.Post;

import java.util.List;

public interface PostRepository extends CrudRepository <Post, Long>{
    List<Post> getAllByUsernameEquals(String username);
    List<Post> findAllByOrderByCreationTimestampDesc();
}
