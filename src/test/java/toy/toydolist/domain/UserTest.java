package toy.toydolist.domain;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UserTest {

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
    public void 사용자_생성(){
        User user = em.find(User.class, userId);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getUsername()).isEqualTo("user1");
        assertThat(user.getPassword()).isEqualTo("123456");
    }

    @Test
    public void 비밀번호_변경(){
        User user = em.find(User.class, userId);

        user.changePassword("654321");

        assertThat(user.getPassword()).isEqualTo("654321");
    }
}