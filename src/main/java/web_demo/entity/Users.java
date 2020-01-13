package web_demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Users {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Id private String username;
    private String password;
    private Integer enabled;


    //Getter
    public Long getId() {return id;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public Integer getEnabled() {return enabled;}

    //Setter
    public void setId (Long id) {this.id = id;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setEnabled(Integer enabled) {this.enabled = enabled;}
}
