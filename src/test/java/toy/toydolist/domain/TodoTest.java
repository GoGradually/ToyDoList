package toy.toydolist.domain;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import toy.toydolist.state.IsComplete;
import toy.toydolist.state.IsOverdue;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static toy.toydolist.state.IsComplete.COMPLETE;
import static toy.toydolist.state.IsComplete.INCOMPLETE;

@DataJpaTest
class TodoTest {

    @Autowired
    EntityManager em;

    Long userId;
    @BeforeEach
    public void beforeEach(){

        User user = new User.UserBuilder()
                .setUsername("user1")
                .setPassword("123456")
                .build();

        em.persist(user);
        userId = user.getId();
    }

    @Test
    public void Todo생성(){
        //given
        Todo todo = new Todo.TodoBuilder()
                .setContents("밥 먹기")
                .setDeadline(LocalDateTime.of(2025, 8, 23, 1,50))
                .build();
        User user = em.find(User.class, userId);
        todo.addTodo(user);
        em.persist(todo);

        // when

        //then
        System.out.println(todo.getId());
        System.out.println(todo.getContents());
        System.out.println(todo.getDeadline());
        System.out.println(todo.getIsOverdue());
        assertThat(todo.getId()).isNotNull();
        assertThat(todo.getContents()).isEqualTo("밥 먹기");
        assertThat(todo.getIsOverdue()).isEqualTo(IsOverdue.CURRENT);
    }
    @Test
    public void Todo_삭제(){
        //given
        Todo todo = new Todo.TodoBuilder()
                .setContents("밥 먹기")
                .setDeadline(LocalDateTime.of(2025, 8, 23, 1,50))
                .build();
        User user = em.find(User.class, userId);
        todo.addTodo(user);
        em.persist(todo);

        //when
        todo.removeTodo();

        Todo findTodo = em.find(Todo.class,todo.getId());

        //then
        assertThat(findTodo.isRemoved()).isTrue();

    }

    @Test
    public void 내용_변경(){
        //given
        Todo todo = new Todo.TodoBuilder()
                .setContents("밥 먹기")
                .setDeadline(LocalDateTime.of(2025, 8, 23, 1,50))
                .build();
        User user = em.find(User.class, userId);
        todo.addTodo(user);
        em.persist(todo);
        //when
        todo.setContents("잠 자기");


        //then
        assertThat(todo.getContents()).isEqualTo("잠 자기");

    }

    @Test
    public void 마감_변경_과거로(){
        //given
        Todo todo = new Todo.TodoBuilder()
                .setContents("밥 먹기")
                .setDeadline(LocalDateTime.of(2025, 8, 23, 1,50))
                .build();
        User user = em.find(User.class, userId);
        todo.addTodo(user);
        em.persist(todo);

        //when
        assertThat(todo.getIsOverdue()).isEqualTo(IsOverdue.CURRENT);
        todo.setDeadline(LocalDateTime.of(2023,8,23,1,50));

        //then
        assertThat(todo.getIsOverdue()).isEqualTo(IsOverdue.OVERDUE);

    }

    @Test
    public void 마감_변경_미래로(){
        //given
        Todo todo = new Todo.TodoBuilder()
                .setContents("밥 먹기")
                .setDeadline(LocalDateTime.of(2023, 8, 23, 1,50))
                .build();
        User user = em.find(User.class, userId);
        todo.addTodo(user);
        em.persist(todo);

        //when
        assertThat(todo.getIsOverdue()).isEqualTo(IsOverdue.OVERDUE);
        todo.setDeadline(LocalDateTime.of(2025,8,23,1,50));

        //then
        assertThat(todo.getIsOverdue()).isEqualTo(IsOverdue.CURRENT);

    }

    @Test
    public void 상태_변경_완료(){
        //given
        Todo todo = new Todo.TodoBuilder()
                .setContents("밥 먹기")
                .setDeadline(LocalDateTime.of(2023, 8, 23, 1,50))
                .build();
        User user = em.find(User.class, userId);
        todo.addTodo(user);
        em.persist(todo);

        //when
        todo.setIsComplete(COMPLETE);

        //then
        assertThat(todo.getIsComplete()).isEqualTo(COMPLETE);

    }

    @Test
    public void 상태_변경_미완료(){
        //given
        Todo todo = new Todo.TodoBuilder()
                .setContents("밥 먹기")
                .setDeadline(LocalDateTime.of(2023, 8, 23, 1,50))
                .build();
        User user = em.find(User.class, userId);
        todo.addTodo(user);
        em.persist(todo);
        todo.setIsComplete(COMPLETE);

        //when

        todo.setIsComplete(INCOMPLETE);
        //then
        assertThat(todo.getIsComplete()).isEqualTo(INCOMPLETE);

    }
}