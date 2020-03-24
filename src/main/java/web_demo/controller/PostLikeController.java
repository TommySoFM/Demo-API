package web_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web_demo.entity.Post;
import web_demo.entity.PostLike;
import web_demo.entity.PostTable;
import web_demo.repository.PostLikeRepository;
import web_demo.service.PostLikeService;
import web_demo.service.PostService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostLikeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostLikeService postLikeService;

    @GetMapping("liked/self")
    public List<PostTable> getLikedByUser () {
        String username = postService.getUsernameFromSession();
        List<PostLike> posts = postLikeRepository.getAllByUsernameEqualsOrderByIdDesc(username);

        List<PostTable> tables = new ArrayList<>();
        for(PostLike post: posts){
            String postText = post.getPost().getPostText();
            String author = post.getPost().getUsername();
            LocalDate creationDate = post.getPost().getCreationTimestamp().toLocalDate();
            int likeCount = post.getPost().getPostLike().size();
            int commentCount = post.getPost().getPostComments().size();
            PostTable table = new PostTable(postText,author,creationDate,likeCount,commentCount);

            tables.add(table);
        }

        return tables;
    }

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
            postLikeService.performUnlike(id, username);
            return new ResponseEntity<String>("unliked", HttpStatus.ACCEPTED);
        }
        else {
            postLikeService.performLike(id, username);
            return new ResponseEntity<String>("liked", HttpStatus.ACCEPTED);
        }
    }
}
