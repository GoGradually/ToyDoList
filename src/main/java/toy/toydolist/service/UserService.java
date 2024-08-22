package toy.toydolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.toydolist.domain.User;
import toy.toydolist.dto.SignUpRequestDto;
import toy.toydolist.dto.UserDto;
import toy.toydolist.repository.UserRepository;
import toy.toydolist.repository.query.UserQueryRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;

    /**
     * 회원가입
     */
    public UserDto signUp(SignUpRequestDto signUpRequestDto){
        User user = new User.UserBuilder()
                .setUsername(signUpRequestDto.getUsername())
                .setPassword(signUpRequestDto.getPassword())
                .build();
        userRepository.save(user);

        return new UserDto(user);
    }

    /**
     * 로그인
     */

    /**
     * 로그아웃
     */
}
