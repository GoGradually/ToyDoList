package toy.toydolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import toy.toydolist.domain.Todo;
import toy.toydolist.domain.User;
import toy.toydolist.dto.TodoDto;
import toy.toydolist.repository.TodoRepository;
import toy.toydolist.repository.UserRepository;
import toy.toydolist.repository.query.TodoQueryRepository;
import toy.toydolist.state.IsComplete;

import java.time.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoQueryRepository todoQueryRepository;
    private final UserRepository userRepository;

    /**
     * 단일 Todo객체 조회
     */
    public TodoDto findTodoDtoById(Long userId, Long todoId){
        return new TodoDto(findTodoById(userId,todoId));
    }


    /**
     * 미래 Todo객체 조회
     */
    @Transactional(readOnly = true)
    public List<TodoDto> searchFutureTodo(Long userId){
        User userById = getUser(userId);

        return todoQueryRepository.searchAllFuture(userById).stream().map(TodoDto::new).toList();
    }


    /**
     * 지난 Todo객체 조회
     */
    @Transactional(readOnly = true)
    public List<TodoDto> searchPastTodo(Long userId, int year, int month){
        User userById = getUser(userId);

        LocalDateTime startOfMonth = YearMonth.of(year, month).atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = YearMonth.of(year, month).plusMonths(1).atDay(1).atStartOfDay().minusSeconds(1);

        return todoQueryRepository.searchByMonth(userById, startOfMonth, endOfMonth).stream().map(TodoDto::new).toList();
    }

    /**
     * Todo업데이트
     * 내용
     * 데드라인
     * 완료 여부
     */
    public TodoDto updateTodoContent(Long userId, Long todoId, String contents){
        Todo todo = findTodoById(userId, todoId);
        todo.setContents(contents);
        return new TodoDto(todo);
    }

    public TodoDto updateTodoDeadline(Long userId, Long todoId, int year, int month, int day){
        Todo todo = findTodoById(userId, todoId);
        LocalDateTime endOfDay = LocalDate.of(year,month,day).atTime(LocalTime.MAX);
        todo.setDeadline(endOfDay);
        return new TodoDto(todo);
    }

    public TodoDto updateTodoIsFinished(Long userId, Long todoId, boolean check){
        Todo todo = findTodoById(userId, todoId);
        if(check){
            todo.setIsComplete(IsComplete.COMPLETE);
        }else{
            todo.setIsComplete(IsComplete.INCOMPLETE);
        }
        return new TodoDto(todo);
    }


    /**
     * 내부 메소드
     */
    private Todo findTodoById(Long userId, Long todoId){

        Optional<User> userById = userRepository.findById(userId);
        Optional<Todo> todoById = todoRepository.findById(todoId);

        if(todoById.isEmpty()){
            throw new NoSuchElementException();
        }
        if(userById.isEmpty() || userById.get() != todoById.get().getUser()){
            throw new SecurityException();
        }
        return todoById.get();
    }
    private User getUser(Long userId) {
        Optional<User> userById = userRepository.findById(userId);

        if(userById.isEmpty()){
            throw new SecurityException();
        }
        return userById.get();
    }

}
