package web_demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import web_demo.entity.Post;
import web_demo.repository.PostRepository;

import java.time.LocalDateTime;

@Service
public class PostService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostRepository postRepository;

    public String getUsernameFromSession(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    //CRUD Type: Get (Read)
    public Page<Post> getPostsByPages(int pageNumber, int size){
        if(pageNumber < 0){
            Pageable sortFirstPage_TimeDesc = PageRequest.of(0, size, Sort.by("creationTimestamp").descending());
            return postRepository.findAll(sortFirstPage_TimeDesc);
        }

        Pageable sortPage_TimeDesc = PageRequest.of(pageNumber, size, Sort.by("creationTimestamp").descending());
        Page<Post> posts = postRepository.findAll(sortPage_TimeDesc);

        int totalPages = posts.getTotalPages();
        int currentPage = posts.getNumber() + 1;

        if( currentPage > totalPages){
            Pageable sortLastPage = PageRequest.of(totalPages-1, size, Sort.by("creationTimestamp").descending());
            return postRepository.findAll(sortLastPage);
        } else{
            return posts;
        }
    }

    //CRUD Type: Post (Create)
    public void savePost(String username, String postText){
        Post post = new Post(username, postText, LocalDateTime.now());
        postRepository.save(post);
    }


    //CRUD Type: Put (Update)
    public void updatePost(Long id, String username, String postText){
        Post targetPost = postRepository.findFirstById(id);
        targetPost.setUsername(username);
        targetPost.setPostText(postText);

        postRepository.save(targetPost);
    }

    public boolean isPostContentUnchanged(Long id, String postText){
        String originalPostText = postRepository.findFirstById(id).getPostText();
        return originalPostText.equals(postText);
    }


    //CRUD Type: Put (Update), Delete
    public boolean isPostExist(Long id){
        return postRepository.existsById(id);
    }

    public boolean isPostOwner(Long id, String username){
        String creatorName = postRepository.findFirstById(id).getUsername();
        return creatorName.equals(username);
    }

    //CRUD Type: Delete
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }
}
