package toy.toydolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toy.toydolist.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
