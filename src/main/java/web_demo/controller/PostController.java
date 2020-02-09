package web_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web_demo.entity.Post;
import web_demo.repository.PostRepository;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping
public class PostController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/post")
    public Page<Post> getAllPost (@RequestParam int page) {
        if(page < 0){
            Pageable sortFirstPage = PageRequest.of(0, 3, Sort.by("creationTimestamp").descending());
            return postRepository.findAll(sortFirstPage);
        }else{
            Pageable sort5_TimeDesc = PageRequest.of(page, 3, Sort.by("creationTimestamp").descending());
            Page<Post> posts = postRepository.findAll(sort5_TimeDesc);
            int totalPages = posts.getTotalPages();
            int currentPage = posts.getNumber() + 1;
            if( currentPage > totalPages){
                Pageable sortLastPage = PageRequest.of(totalPages-1, 3, Sort.by("creationTimestamp").descending());
                return postRepository.findAll(sortLastPage);
            } else{
                return posts;
            }
        }
    }

    @GetMapping(value = "/post/user")
    public Page<Post> getPersonPost(@RequestParam int page) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable sort5_TimeDesc = PageRequest.of(page, 3, Sort.by("creationTimestamp").descending());

        return postRepository.getAllByUsernameEquals(username, sort5_TimeDesc);
    }

    @PostMapping(value = "/post")
    public ResponseEntity<String> addNewPost (String postText) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = new Post(username, postText, LocalDateTime.now());
        postRepository.save(post);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        responseHeaders.setLocation(locationUri);
        return new ResponseEntity<>("Post Success", responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/post/{id}")
    public ResponseEntity<String> updatePost (@PathVariable Long id, String postText){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!postRepository.existsById(id)){
            return new ResponseEntity<String>("Post Update Failed: Post Not Exist!", HttpStatus.NO_CONTENT);
        }

        Post targetPost = postRepository.findFirstById(id);

        if(!username.equals(targetPost.getUsername())){
            return new ResponseEntity<String>("Post Update Failed: Bad Credentials!", HttpStatus.FORBIDDEN);
        }else if (postText.equals(targetPost.getPostText())){
            return new ResponseEntity<String>("Post Update Failed: Same Content!", HttpStatus.FORBIDDEN);
        }else {
            targetPost.setUsername(username);
            targetPost.setPostText(postText);
            postRepository.save(targetPost);
            return new ResponseEntity<String>("Post Update Success!", HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping(value = "/post/{id}")
    public ResponseEntity<String> deletePost (@PathVariable Long id){
        if (!postRepository.existsById(id)){
            return new ResponseEntity<String>("Post Delete Failed: Post Not Exist!", HttpStatus.NO_CONTENT);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String creator = postRepository.findFirstById(id).getUsername();

        if( username.equals(creator)) {
            postRepository.deleteById(id);
            return new ResponseEntity<String>("Post Delete Success!", HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<String>("Post Delete Failed: Bad Credentials!", HttpStatus.FORBIDDEN);
        }
    }
}
