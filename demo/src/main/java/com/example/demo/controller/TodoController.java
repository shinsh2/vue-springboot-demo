package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TodoController {

    private final TodoService todoService;

    /**
     * Todo 등록
     * @return
     */
    @PostMapping("/todos/save")
    public String createJsonTodo(@RequestBody @Valid TodoForm form, BindingResult bindingResult){
        log.info("Post : Todo Save");

        return validation(form, bindingResult);
    }

    /**
     * Todo 목록
     * @return
     */
    @GetMapping("/todos/{orderState}")
    public List<Todo> list(@PathVariable("orderState") Boolean orderState){
        log.info("Get : Todos List");

        return todoService.findTodos(orderState);
    }

    /**
     * Todo 완료 상태 업데이트
     * @return
     */
    @PutMapping("/todos/{id}")
    public String updateTodo(
            @PathVariable("id") Long id,
            @RequestBody UpdateTodoRequest request
    ){
        log.info("Put : Todo update");

        todoService.updateTodoComplted(id, request.isCompleted());

        Todo findTodo = todoService.findOne(id);

        if(request.isCompleted() == findTodo.isCompleted()){
            return "ok";
        } else {
            return "fail";
        }
    }

    /**
     * Todo 삭제(DB 업데이트)
     */
    @PutMapping("/todos/delete/{id}")
    public String deleteTodo(
            @PathVariable("id") Long id
    ){
        log.info("Delete : Todo Delete");

        todoService.updateTodoUseYn(id);

        Todo findTodo = todoService.findOne(id);

        if(findTodo.getUseYn().equals("N")){
            return "ok";
        } else {
            return "fail";
        }
    }

    @PutMapping("/todos/clear")
    public String clearAllTodo(){
        log.info("Clear : Todo All Clear");

        int result = todoService.updateTodoAllClear();

        if(result > 0){
            return "ok";
        } else {
            return "fail";
        }
    }

    private String validation(@Valid @RequestBody TodoForm form, BindingResult bindingResult) {
    	log.debug(form.toString());
        if(bindingResult.hasErrors()){
        	log.debug(bindingResult.toString());
            return "todo error";
        }

        Todo todo = new Todo();
        todo.setItem(form.getItem());
        todo.setCompleted(form.isCompleted());
        todo.setDate(form.getDate());
        todo.setTime(form.getTime());
        todo.setWriteDate(LocalDateTime.now());
        todo.setUpdateDate(LocalDateTime.now());

        todoService.save(todo);

        return "ok";
    }

    @Data
    static class UpdateTodoRequest{

        private Long id;
        @NotEmpty
        private boolean completed;

    }
}
