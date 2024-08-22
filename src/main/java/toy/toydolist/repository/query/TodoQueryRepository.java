package toy.toydolist.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.toydolist.domain.Todo;
import toy.toydolist.domain.User;

import java.time.LocalDateTime;
import java.util.List;

import static toy.toydolist.domain.QTodo.todo;

@Repository
@RequiredArgsConstructor
public class TodoQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Todo> searchByMonth(User user, LocalDateTime from, LocalDateTime to){
        return queryFactory
                .selectFrom(todo)
                .where(todo.deadline.between(from, to), userEq(user))
                .fetch();
    }
    public List<Todo> searchAllFuture(User user){
        return queryFactory
                .select(todo)
                .from(todo)
                .where(userEq(user))
                .fetch();
    }

    private BooleanExpression userEq(User user){
        return user != null ? todo.isRemoved.isFalse().and(todo.user.eq(user)) : null;
    }
}
