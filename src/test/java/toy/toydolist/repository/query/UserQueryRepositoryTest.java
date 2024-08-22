package toy.toydolist.repository.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import toy.toydolist.domain.Todo;
import toy.toydolist.domain.User;
import toy.toydolist.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class UserQueryRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired UserQueryRepository userQueryRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void beforeEach(){
        for (int i = 0; i < 30; i++) {

            User user = new User.UserBuilder()
                    .setUsername("나 - " + i)
                    .setPassword("123456")
                    .build();
            userRepository.save(user);
        }
        for (int i = 0; i < 40; i++) {
            User user = new User.UserBuilder()
                    .setUsername("너 - " + i)
                    .setPassword("123456")
                    .build();
            userRepository.save(user);
        }
        em.flush();
        em.clear();
    }

    //Todo
    @Test
    public void 사용자_찾기_공백(){
        //given
        
        //when
        
        //then
        
    }

    @Test
    public void 사용자_찾기_나(){
        //given
        List<User> byUsername = userQueryRepository.findByUsername("나", PageRequest.of(1, 5)).stream().toList();
        //when

        //then
        assertThat(byUsername.size()).isEqualTo(5);
    }
    @Test
    public void 사용자_찾기_너(){
        //given
        //when
        List<User> byUsername = userQueryRepository.findByUsername("너", PageRequest.of(7, 5)).stream().toList();

        //then
        assertThat(byUsername.size()).isEqualTo(5);

    }
    @Test
    public void 사용자_찾기_잘못된_시작(){
        //given
        //when
        List<User> byUsername = userQueryRepository.findByUsername("3", PageRequest.of(7, 5)).stream().toList();

        //then
        assertThat(byUsername.size()).isEqualTo(0);

    }

}