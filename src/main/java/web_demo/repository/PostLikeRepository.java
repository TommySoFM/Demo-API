package web_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import web_demo.entity.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Boolean existsByPostIdAndUsername (Long postId, String username);

    @Transactional
    void deleteByPostIdAndUsername (Long postId, String username);
}
