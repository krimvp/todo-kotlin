package co.krimvp.todolist

import co.krimvp.todolist.todo.TodoDTO
import co.krimvp.todolist.todo.TodoEntity
import co.krimvp.todolist.todo.TodoRepository
import co.krimvp.todolist.todo.TodoServiceImpl
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.sun.tools.javac.comp.Todo
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.any
import org.mockito.Mockito.verify
import java.util.*


class TodoServiceTest(){
    private val fixTodoEntity = TodoEntity()
    private val mockedRepo = mock<TodoRepository> {
        on { save(ArgumentMatchers.any<TodoEntity>())} doReturn fixTodoEntity
        on { findById(ArgumentMatchers.anyLong())} doReturn Optional.of(fixTodoEntity)

    }
    private val sut = TodoServiceImpl(mockedRepo)

    @Test
    fun `service calls repo for findAll()`(){
        sut.findAll()
        verify(mockedRepo).findAll()
    }

    @Test
    fun `service calls repo for deleteById()`(){
        val todoEntity = TodoEntity()
        sut.deleteById(todoEntity.id)
        verify(mockedRepo).deleteById(todoEntity.id)
    }

    @Test
    fun `service calls repo for findById()`() {
        val todoEntity = TodoEntity()
        sut.findById(todoEntity.id)
        verify(mockedRepo).findById(todoEntity.id)
    }

    @Test
    fun `service calls repo for save()`() {
        sut.save(TodoDTO("Title", "Description"))
        verify(mockedRepo).save(ArgumentMatchers.any())
    }

    @Test
    fun `service calls repo for update()`() {
        sut.update(0L, TodoDTO("Title", "Description", true))
        verify(mockedRepo).save(ArgumentMatchers.any())
    }
}