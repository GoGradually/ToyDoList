package toy.toydolist.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import toy.toydolist.domain.Todo;
import toy.toydolist.domain.User;
import toy.toydolist.state.IsComplete;
import toy.toydolist.state.IsOverdue;

import java.time.LocalDateTime;

@Data
public class TodoDto {
    private Long id;

    private LocalDateTime deadline;

    @Length(max = 20)
    private String contents;

    private IsComplete isComplete;

    private IsOverdue isOverdue;

    public TodoDto(Todo todo){
        id = todo.getId();
        deadline = todo.getDeadline();
        contents = todo.getContents();
        isComplete = todo.getIsComplete();
        isOverdue = todo.getIsOverdue();
    }
}
