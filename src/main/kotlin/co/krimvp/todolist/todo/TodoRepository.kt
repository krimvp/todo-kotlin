package co.krimvp.todolist.todo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository: CrudRepository<TodoEntity, Long> {
    fun findByTitle(name: String): List<TodoEntity>
}