package toy.toydolist.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"teamName"})
        }
)
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String teamName;

    @OneToMany(mappedBy = "team")
    private List<UserAndTeam> userAndTeamList = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<TeamTodo> teamTodoList = new ArrayList<>();

}
