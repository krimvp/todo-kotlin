package co.krimvp.todolist.todo

data class TodoDTO(
        val title: String,
        val description: String,
        val isFinished: Boolean = false
) {
}