package web_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web_demo.service.PostCommentService;
import web_demo.service.PostService;

@RestController
@RequestMapping("/post")
public class PostCommentController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostCommentService postCommentService;

    @PostMapping("/{id}/comment")
    public ResponseEntity<String> newComment (@PathVariable Long id, String commentText){
        String username = postService.getUsernameFromSession();
        postCommentService.savePostComment(username, commentText, id);

        return new ResponseEntity<String>("Comment Success", HttpStatus.ACCEPTED);
    }
}
