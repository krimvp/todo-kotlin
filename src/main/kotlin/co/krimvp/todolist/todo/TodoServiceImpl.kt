package co.krimvp.todolist.todo

import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Service
class TodoServiceImpl(val todoRepo: TodoRepository) : TodoService {
    override fun update(todoId: Long, todoDTO: TodoDTO) {
        var previousTodo = todoRepo.findById(todoId).get()

        var todoEntity = TodoEntity(
                id = previousTodo.id,
                title = todoDTO.title,
                description = todoDTO.description,
                isFinished = todoDTO.isFinished,
                createdAt = previousTodo.createdAt,
                modifiedAt = ZonedDateTime.now(ZoneId.of("UTC")),
                finishedAt = if (todoDTO.isFinished) ZonedDateTime.now(ZoneId.of("UTC"))
                             else previousTodo.finishedAt
        )

        todoRepo.save(todoEntity)
    }

    override fun deleteById(id: Long) {
        todoRepo.deleteById(id)
    }

    override fun findById(id: Long): Optional<TodoEntity> {
        return todoRepo.findById(id)
    }

    override fun findAll(): MutableIterable<TodoEntity> {
        return todoRepo.findAll()
    }

    override fun save(todo: TodoDTO): TodoEntity {
        var todoEntity = TodoEntity(
                title = todo.title,
                description = todo.description
        )
        return todoRepo.save(todoEntity)
    }

}