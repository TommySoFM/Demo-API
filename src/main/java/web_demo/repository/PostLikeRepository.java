package web_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web_demo.entity.PostLike;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findFirstByUsernameAndPostId(String username, Long postId);
}
