package web_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web_demo.entity.PostLike;
import web_demo.repository.PostLikeRepository;

@Service
public class PostLikeService {

    @Autowired
    private PostLikeRepository postLikeRepository;

    public boolean isPostLiked(Long postId, String username){
        return postLikeRepository.existsByPostIdAndUsername(postId, username);
    }

    public void performLike(Long postId, String username){
        PostLike postLike = new PostLike(username, (short) 1, postId);
        postLikeRepository.save(postLike);
    }

    public void performUnlike(Long postId, String username){
        postLikeRepository.deleteByPostIdAndUsername(postId, username);
    }
}
