package web_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web_demo.entity.Post;
import web_demo.entity.PostLike;

import web_demo.entity.PostTable;
import web_demo.repository.PostRepository;
import web_demo.service.PostService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @GetMapping("/post")
    public Page<Post> getPost (@RequestParam int page) {
        return postService.getPostsByPages(page,3);
    }

    @GetMapping("/post/self")
    public List<PostTable> getAllByUser () {
        String username = postService.getUsernameFromSession();
        List<Post> posts = postRepository.getAllByUsernameEqualsOrderByCreationTimestampDesc(username);

        List<PostTable> tables = new ArrayList<>();
        for(Post post: posts){
            String postText = post.getPostText();
            String author = post.getUsername();
            LocalDate creationDate = post.getCreationTimestamp().toLocalDate();
            int likeCount = post.getPostLike().size();
            int commentCount = post.getPostComments().size();
            PostTable table = new PostTable(postText,author,creationDate,likeCount,commentCount);

            tables.add(table);
        }

        return tables;
    }

    @PostMapping(value = "/post")
    public ResponseEntity<String> addNewPost (String postText) {
        String username = postService.getUsernameFromSession();
        postService.savePost(username, postText);

        return new ResponseEntity<String>("Post Success" ,HttpStatus.CREATED);
    }

    @PutMapping(value = "/post/{id}")
    public ResponseEntity<String> updatePost (@PathVariable Long id, String postText){
        String username = postService.getUsernameFromSession();

        if (!postService.isPostExist(id)){
            return new ResponseEntity<String>("Post Update Failed: Post Not Exist!", HttpStatus.NO_CONTENT);
        }
        if(!postService.isPostOwner(id, username)){
            return new ResponseEntity<String>("Post Update Failed: Bad Credentials!", HttpStatus.FORBIDDEN);
        }
        if (postService.isPostContentUnchanged(id, postText)){
            return new ResponseEntity<String>("Post Update Failed: Same Content!", HttpStatus.FORBIDDEN);
        }

        postService.updatePost(id, username, postText);
        return new ResponseEntity<String>("Post Update Success!", HttpStatus.ACCEPTED);
    }


    @DeleteMapping(value = "/post/{id}")
    public ResponseEntity<String> deletePost (@PathVariable Long id){
        String username = postService.getUsernameFromSession();

        if (!postService.isPostExist(id)){
            return new ResponseEntity<String>("Post Delete Failed: Post Not Exist!", HttpStatus.NO_CONTENT);
        }
        if(!postService.isPostOwner(id,username)) {
            return new ResponseEntity<String>("Post Delete Failed: Bad Credentials!", HttpStatus.FORBIDDEN);
        }

        postService.deletePost(id);
        return new ResponseEntity<String>("Post Delete Success!", HttpStatus.ACCEPTED);
    }
}

//    @GetMapping(value = "/post/user")
//    public Page<Post> getPersonPost(@RequestParam int page) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Pageable sort5_TimeDesc = PageRequest.of(page, 3, Sort.by("creationTimestamp").descending());
//
//        return postRepository.getAllByUsernameEquals(username, sort5_TimeDesc);
//    }

//     Response Header in ResponseEntity:
//        HttpHeaders responseHeaders = new HttpHeaders();
//        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
//        responseHeaders.setLocation(locationUri);
