package web_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String postText;

    @JsonBackReference
    @ManyToOne (fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                       CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn (name="user_id")
    private User user;

    //Constructor
    public Post() {
    }

    public Post (String username, String postText) {
        this.username = username;
        this.postText = postText;
    }

    //Getter
    public Long getId() {return id;}
    public String getUsername() {return username;}
    public String getPostText() {return postText;}
    public User getUser() {return user;}

    //Setter
    public void setId(Long id) {this.id = id;}
    public void setUsername(String username) {this.username = username;}
    public void setPostText(String postText) {this.postText = postText;}
    public void setUser(User user) {this.user = user;}
}
