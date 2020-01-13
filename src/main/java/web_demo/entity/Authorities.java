package web_demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Authorities {


    @GeneratedValue ( strategy = GenerationType.AUTO)

    private Long id;
    @Id private String username;
    private  String authority;

    public Long getId() {return id;}
    public String getUsername() {return username;}
    public String getAuthority() {return authority;}

    public void setId(Long id) {this.id = id;}
    public void setUsername(String username) {this.username = username;}
    public void setAuthority(String authority) {this.authority = authority;}
}
