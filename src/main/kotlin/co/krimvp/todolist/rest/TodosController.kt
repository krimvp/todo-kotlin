package co.krimvp.todolist.rest

import co.krimvp.todolist.todo.TodoDTO
import co.krimvp.todolist.todo.TodoEntity
import co.krimvp.todolist.todo.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*



@RestController
class TodosController(val todoService: TodoService) {


    @GetMapping("/todos")
    fun getAllTodos(): ResponseEntity<Iterable<TodoEntity>> {
        return ResponseEntity<Iterable<TodoEntity>>(todoService.findAll(), HttpStatus.OK)
    }

    @PostMapping("/todos")
    fun postTodo(@RequestBody todo: TodoDTO): ResponseEntity<TodoEntity> {
        var result = todoService.save(todo)
        return ResponseEntity<TodoEntity>(result, HttpStatus.CREATED)
    }

    @GetMapping("/todo/{todoId}")
    fun getTodoById(@PathVariable todoId: Long): ResponseEntity<TodoEntity> {
        return ResponseEntity<TodoEntity>(todoService.findById(todoId).get(), HttpStatus.OK)
    }

    @PutMapping("/todo/{todoId}")
    fun putTodo(@PathVariable todoId: Long, @RequestBody todo: TodoDTO): Any? {
        todoService.update(todoId, todo)
        return ResponseEntity<Any>(null, HttpStatus.OK)
    }

    @DeleteMapping("/todo/{todoId}")
    fun deleteTodo(@PathVariable todoId: Long): Any? {
        todoService.deleteById(todoId)
        return ResponseEntity<Any>(null, HttpStatus.NO_CONTENT)
    }
}