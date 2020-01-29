package web_demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private Integer enabled;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",
               cascade = {CascadeType.DETACH, CascadeType.MERGE,
                          CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Post> posts;

    //Constructor
    public User() {
    }

    //Getter
    public Long getId() {return id;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public Integer getEnabled() {return enabled;}
    public List<Post> getPosts() {return posts;}

    //Setter
    public void setId (Long id) {this.id = id;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setEnabled(Integer enabled) {this.enabled = enabled;}
    public void setPosts(List<Post> posts) {this.posts = posts;}
}
