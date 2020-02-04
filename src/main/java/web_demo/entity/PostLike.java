package web_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Short liked;
    @Column (name="post_id")
    private Long postId;

    @JsonBackReference
    @ManyToOne (fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn (name="post_id", insertable = false, updatable = false)
    private Post post;


    public PostLike(){}
    public PostLike(String username, Short liked, Long postId){
        this.username = username;
        this.liked = liked;
        this.postId = postId;
    }


    public Long getId() {return id;}
    public String getUsername() {return username;}
    public short getLiked() {return liked;}
    public Long getPostId() {return postId;}

    public Post getPost() {return post;}


    public void setId(Long id) {this.id = id;}
    public void setUsername(String username) {this.username = username;}
    public void setLiked(short liked) {this.liked = liked;}
    public void setPostId(Long postId) {this.postId = postId;}

    public void setPost(Post post) {this.post = post;}
}
