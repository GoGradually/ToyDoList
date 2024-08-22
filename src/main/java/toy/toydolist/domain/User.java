package toy.toydolist.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;

    @Lob
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Todo> todoList = new ArrayList<>();

    @Getter
    public static class UserBuilder{
        private String username;
        private String password;

        public UserBuilder setUsername(String username){
            this.username = username;
            return this;
        }

        public UserBuilder setPassword(String password){
            this.password = password;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    protected User(){

    }

    private User(UserBuilder builder){
        this.username = builder.getUsername();
        this.password = builder.getPassword();
    }

    public void changePassword(String password){
        this.password = password;
    }
}
