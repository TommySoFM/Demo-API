package web_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web_demo.service.PostLikeService;
import web_demo.service.PostService;

@RestController
@RequestMapping("/post")
public class PostLikeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostService postService;

    @Autowired
    private PostLikeService postLikeService;

    @PostMapping("/{id}/isLiked")
    public ResponseEntity<String> isLiked (@PathVariable Long id){
        String username = postService.getUsernameFromSession();

        if (postLikeService.isPostLiked(id, username)){
            return new ResponseEntity<String>("true", HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<String>("false", HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> toggleLike (@PathVariable Long id){
        String username = postService.getUsernameFromSession();

        if (postLikeService.isPostLiked(id, username)){
            logger.warn(">>>> Before Unlike <<<<");
            postLikeService.performUnlike(id, username);
            return new ResponseEntity<String>("unliked", HttpStatus.ACCEPTED);
        }
        else {
            postLikeService.performLike(id, username);
            return new ResponseEntity<String>("liked", HttpStatus.ACCEPTED);
        }
    }
}
