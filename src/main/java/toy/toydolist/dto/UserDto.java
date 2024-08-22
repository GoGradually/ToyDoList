package toy.toydolist.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import toy.toydolist.domain.Todo;
import toy.toydolist.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private Long id;
    @Length(min = 6, max = 20)
    private String username;


    public UserDto(User user){
        id = user.getId();
        username = user.getUsername();
    }
}
