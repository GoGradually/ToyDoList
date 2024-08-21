package toy.toydolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toy.toydolist.domain.TeamTodo;

@Repository
public interface TeamTodoRepository extends JpaRepository<TeamTodo, Long> {
}
