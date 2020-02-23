package web_demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name="authorities")
public class Authority {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @JsonIgnore
    private  String authority;

    //Constructor
    public Authority() {
    }

    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    //Getter
    public Long getId() {return id;}
    public String getUsername() {return username;}
    public String getAuthority() {return authority;}

    //Setter
    public void setId(Long id) {this.id = id;}
    public void setUsername(String username) {this.username = username;}
    public void setAuthority(String authority) {this.authority = authority;}
}
