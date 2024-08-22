package toy.toydolist.repository.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.toydolist.domain.Todo;
import toy.toydolist.domain.User;
import toy.toydolist.repository.TodoRepository;
import toy.toydolist.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TodoQueryRepositoryTest {

    @Autowired
    private TodoQueryRepository todoQueryRepository;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private EntityManager em;
    private Long userId;

    @BeforeEach
    public void beforeEach(){
        User user = new User.UserBuilder()
                .setUsername("나")
                .setPassword("123456")
                .build();
        em.persist(user);
        userId = user.getId();
        for (int i = 0; i < 30; i++) {
            Todo todo = new Todo.TodoBuilder()
                    .setContents("밥 먹기 " + i)
                    .setDeadline(LocalDateTime.of(2024, 7, 1 + i, 0, 5))
                    .build();
            todo.addTodo(user);
            todoRepository.save(todo);
        }
        for (int i = 0; i < 30; i++) {
            Todo todo = new Todo.TodoBuilder()
                    .setContents("밥 먹기 " + i)
                    .setDeadline(LocalDateTime.of(2024, 9, 1 + i, 0, 5))
                    .build();
            todoRepository.save(todo);
        }
        em.flush();
        em.clear();
    }

    @Test
    public void Todo찾기_과거(){
        //given
        User user = em.find(User.class,userId);
        LocalDateTime startOfMonth = YearMonth.of(2024, 7).atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = YearMonth.of(2024, 7).plusMonths(1).atDay(1).atStartOfDay().minusSeconds(1);
        //when
        List<Todo> todos = todoQueryRepository.searchByMonth(user, startOfMonth, endOfMonth);
        //then
        assertThat(todos.size()).isEqualTo(30);

    }
    @Test
    public void Todo찾기_미래(){
        //given
        User user = em.find(User.class,userId);
        //when
        List<Todo> todos = todoQueryRepository.searchAllFuture(user);
        //then
        assertThat(todos.size()).isEqualTo(30);
    }
    @Test
    public void Todo찾기_존재X(){
        //given
        User user = em.find(User.class,userId);
        LocalDateTime startOfMonth = YearMonth.of(2024, 6).atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = YearMonth.of(2024, 6).plusMonths(1).atDay(1).atStartOfDay().minusSeconds(1);
        //when
        List<Todo> todos = todoQueryRepository.searchByMonth(user, startOfMonth, endOfMonth);
        //then
        assertThat(todos.size()).isEqualTo(0);

    }

}