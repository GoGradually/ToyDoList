package toy.toydolist.repository.query;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import toy.toydolist.domain.QTodo;
import toy.toydolist.domain.QUser;
import toy.toydolist.domain.Todo;
import toy.toydolist.domain.User;

import java.time.LocalDateTime;
import java.util.List;

import static toy.toydolist.domain.QTodo.todo;
import static toy.toydolist.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<User> findByUsername(String username, Pageable pageable){
        List<User> contents = queryFactory
                .selectFrom(user)
                .where(user.username.startsWith(username))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(user.count())
                .from(user)
                .where(user.username.startsWith(username));

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }
}
