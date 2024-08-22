package toy.toydolist.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import toy.toydolist.state.IsComplete;
import toy.toydolist.state.IsOverdue;

import java.time.LocalDateTime;

@Entity
@Getter
public class Todo {
    @Id
    @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Lob
    private String contents;

    @Enumerated(EnumType.STRING)
    private IsComplete isComplete;

    @Enumerated(EnumType.STRING)
    private IsOverdue isOverdue;

    private boolean isRemoved = false;

    @Getter
    public static class TodoBuilder{
        private LocalDateTime deadline;
        private String contents;

        public TodoBuilder setDeadline(LocalDateTime time){
            deadline = time;
            return this;
        }
        public TodoBuilder setContents(String contents){
            this.contents = contents;
            return this;
        }

        public Todo build(){
            return new Todo(this);
        }
    }

    protected Todo(){
    }

    private Todo(TodoBuilder builder){
        deadline = builder.getDeadline();
        contents = builder.getContents();
        isComplete = IsComplete.INCOMPLETE;
        isRemoved = false;
        changeOverDue();
    }

    /* 변경 가능한 값들 변경 메소드 */

    public void setDeadline(LocalDateTime time){
        deadline = time;
        changeOverDue();
    }

    public void setIsComplete(IsComplete isComplete){
        this.isComplete = isComplete;
    }



    public void addTodo(User user){
        this.user = user;
        user.getTodoList().add(this);
    }
    public void removeTodo(){
        isRemoved = true;
    }


    /* 변경 자동화 메소드 */
    private void changeOverDue(){
        if (deadline.isAfter(LocalDateTime.now())){
            isOverdue = IsOverdue.CURRENT;
        }else{
            isOverdue = IsOverdue.OVERDUE;
        }
    }
}
