package web_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web_demo.entity.Post;
import web_demo.repository.PostRepository;

import java.net.URI;

@RestController
@RequestMapping
public class PostController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/post")
    public Iterable<Post> getAllPost () {
        return postRepository.findAll();
//    public ResponseEntity<List<Post>> getAllPost () {
//        Iterable<Post> posts = postRepository.findAll();
//        List<Post> listPosts = (List<Post>) posts;
//        return new ResponseEntity<List<Post>>(listPosts, HttpStatus.OK);
    }

    @GetMapping(value = "/post/{username}")
    public Iterable<Post> getPersonPost(@PathVariable String username) {
        return postRepository.getAllByUsernameEquals(username);
    }

    @PostMapping(value = "/post")
    public ResponseEntity<?> addNewPost (String username, String postText) {
        Post post = new Post(username, postText);
        postRepository.save(post);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        responseHeaders.setLocation(locationUri);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/post/{id}")
    public ResponseEntity<?> updatePost (@PathVariable Long id, String username, String postText){
        if (!postRepository.existsById(id)){
            throw new CustomExceptionController("Post not exist!");
        }

        postRepository.findById(id).map(
            post -> {
                post.setUsername(username);
                post.setPostText(postText);
                return postRepository.save(post);
        });
        return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/post/{id}")
    public ResponseEntity<?> deletePost (@PathVariable Long id){
        if (!postRepository.existsById(id)){
            throw new CustomExceptionController("Post not exist!");
        }

        postRepository.deleteById(id);
        return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
    }
}
