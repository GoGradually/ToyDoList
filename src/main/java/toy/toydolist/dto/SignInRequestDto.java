package toy.toydolist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SignInRequestDto {
    @NotNull
    @Length(min=6, max=20)
    String username;

    @NotNull
    @Length(min=6, max=20)
    String password;
}

