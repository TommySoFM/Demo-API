package web_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web_demo.entity.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findFirstByUsernameAndPostId(String username, Long postId);
}
