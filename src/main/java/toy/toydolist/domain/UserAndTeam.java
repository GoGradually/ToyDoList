package toy.toydolist.domain;

import jakarta.persistence.*;
import lombok.Getter;
import toy.toydolist.state.Role;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class UserAndTeam {
    @Id
    @GeneratedValue
    @Column(name = "user_and_team_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
