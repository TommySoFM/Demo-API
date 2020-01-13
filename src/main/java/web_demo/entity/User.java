package web_demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private String email;


    //Getter
    public Integer getId() {return id;}
    public String getName() {return name;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}

    //Setter
    public void setId (Integer id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setPassword(String password) {this.password = password;}
    public void setEmail(String email) {this.email = email;}
}
