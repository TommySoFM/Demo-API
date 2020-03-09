package web_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import web_demo.serializer.JsonDateSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String commentText;
    private LocalDateTime creation_timestamp;
    @Column (name="post_id")
    private Long postId;

    @JsonBackReference
    @ManyToOne (fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn (name ="post_id", insertable = false, updatable = false)
    private Post post;

    public PostComment(){};
    public PostComment(String username, String commentText, LocalDateTime creation_timestamp, Long postId){
        this.username = username;
        this.commentText = commentText;
        this.creation_timestamp = creation_timestamp;
        this.postId = postId;
    }

    public Long getId() {return id;}
    public String getUsername() {return username;}
    public String getCommentText() {return commentText;}

    @JsonSerialize(using= JsonDateSerializer.class)
    public LocalDateTime getCreation_timestamp() {return creation_timestamp;}
    public Long getPostId() {return postId;}

    public Post getPost() {return post;}

    public void setId(Long id) {this.id = id;}
    public void setUsername(String username) {this.username = username;}
    public void setCommentText(String commentText) {this.commentText = commentText;}
    public void setCreation_timestamp(LocalDateTime creation_timestamp) {this.creation_timestamp = creation_timestamp;}
    public void setPostId(Long postId) {this.postId = postId;}

    public void setPost(Post post) {this.post = post;}
}
