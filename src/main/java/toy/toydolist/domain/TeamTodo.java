package toy.toydolist.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TeamTodo {
    @Id
    @GeneratedValue
    private Long id;
}
