package co.krimvp.todolist.todo

import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

interface TodoService{
    fun findAll(): MutableIterable<TodoEntity>
    fun save(todo: TodoDTO): TodoEntity
    fun findById(id: Long): Optional<TodoEntity>
    fun deleteById(id: Long)
    fun update(todoId: Long, todoDTO: TodoDTO)

}


