package toy.toydolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.toydolist.domain.UserAndTeam;

public interface UserAndTeamRepository extends JpaRepository<UserAndTeam, Long> {
}
