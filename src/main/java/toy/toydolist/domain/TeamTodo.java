package toy.toydolist.domain;

import jakarta.persistence.*;
import lombok.Getter;
import toy.toydolist.state.IsComplete;
import toy.toydolist.state.IsOverdue;

import java.time.LocalDateTime;

@Entity
@Getter
public class TeamTodo {
    @Id
    @GeneratedValue
    @Column(name = "team_todo_id")
    private Long id;

    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Lob
    private String contents;

    @Enumerated(EnumType.STRING)
    private IsComplete isComplete;

    @Enumerated(EnumType.STRING)
    private IsOverdue isOverdue;
}
