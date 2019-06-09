package hr.fer.tel.project.mreznikviz.MrezniKvizService;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "Users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "FirstName is mandatory")
    private String firstName;
    @NotBlank(message = "UserName is mandatory")
    private String userName;
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotNull(message = "score is mandatory")
    private Long score;

    public User(Long id, String firstName, String userName, String email, String password, Long score){
        this.id = id;
        this.firstName = firstName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.score = score;
    }

    public User(){ }




    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }
}

