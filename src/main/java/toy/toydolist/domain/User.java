package toy.toydolist.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(uniqueConstraints = {
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
}
