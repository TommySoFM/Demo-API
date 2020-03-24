package web_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import web_demo.entity.Post;
import web_demo.entity.PostLike;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Boolean existsByPostIdAndUsername (Long postId, String username);
    List<PostLike> getAllByUsernameEqualsOrderByIdDesc(String username);

    @Transactional
    void deleteByPostIdAndUsername (Long postId, String username);
}
