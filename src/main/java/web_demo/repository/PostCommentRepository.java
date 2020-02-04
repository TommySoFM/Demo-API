package web_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web_demo.entity.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
