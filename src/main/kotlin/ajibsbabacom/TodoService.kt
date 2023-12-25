import ajibsbabacom.models.Task

class TodoService {
    private val tasks = mutableListOf<Task>()

    fun getAllTasks(): List<Task> = tasks.toList()

    fun getTaskbyId(id: Int): Task? = tasks.find { it.id == id}

    fun addTask(title: String, description: String): Task {
        val newId = if (tasks.isEmpty()) 1 else tasks.maxOf { it.id } + 1
        val newTask = Task(newId, title, description, false)
        tasks.add(newTask)
        return newTask
    }

    fun updateTask(id: Int, title: String, description: String, completed: Boolean): Task? {
        val existingTask = getTaskbyId(id)
        return if (existingTask != null) {
            tasks.remove(existingTask)
            val updatedTask = Task(id, title, description, completed)
            tasks.add(updatedTask)
            updatedTask
        } else {
            null
        }
    }

    fun deleteTask(id: Int): Boolean? = tasks.removeIf { it.id == id}
}