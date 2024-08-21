package toy.toydolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toy.toydolist.domain.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
