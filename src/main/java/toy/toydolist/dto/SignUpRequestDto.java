package toy.toydolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SignUpRequestDto {
    @NotNull
    @NotBlank
    @Length(min=6, max=20)
    String username;

    @NotNull
    @NotBlank
    @Length(min=6, max=20)
    String password;
}
