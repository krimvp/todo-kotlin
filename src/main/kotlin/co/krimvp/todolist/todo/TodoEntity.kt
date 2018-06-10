package co.krimvp.todolist.todo

import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "todos")
data class TodoEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,

        val title: String = "",
        val description: String = "",
        val isFinished: Boolean = false,

        val createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("UTC")),
        val modifiedAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("UTC")),
        val finishedAt: ZonedDateTime? = null
        )

