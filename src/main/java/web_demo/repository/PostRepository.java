package web_demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import web_demo.entity.Post;

import org.springframework.data.domain.Pageable;

public interface PostRepository extends CrudRepository <Post, Long>{
    Page<Post> findAll(Pageable pageable);
    Post findFirstById(Long id);
}

//    Page<Post> getAllByUsernameEquals(String username, Pageable pageable);
