package web_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web_demo.entity.PostComment;
import web_demo.repository.PostCommentRepository;

import java.time.LocalDateTime;

@Service
public class PostCommentService {

    @Autowired
    private PostCommentRepository postCommentRepository;

    public void savePostComment(String username, String commentText, Long id) {
        PostComment postComment = new PostComment(username, commentText, LocalDateTime.now(), id);
        postCommentRepository.save(postComment);
    }
}
