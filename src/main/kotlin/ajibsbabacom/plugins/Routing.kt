package ajibsbabacom.plugins

import TodoService
import ajibsbabacom.models.Task
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val todoService = TodoService()

    routing {
        route("/") {
            get {
                call.respondText("Welcome to Todo List API")
            }
        }
        route("/api/tasks") {
            get {
                call.respond(todoService.getAllTasks())
            }
            get("/{id}") {
                val taskId = call.parameters["id"]?.toIntOrNull()
                if (taskId != null) {
                    val task = todoService.getTaskbyId(taskId)
                    if (task != null) {
                        call.respond(task)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Task not found")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid task ID")
                }
            }
            post {
                val taskRequest = call.receive<Task>()
                val newTask = todoService.addTask(taskRequest.title, taskRequest.description)
                call.respond(HttpStatusCode.Created, newTask)
            }
            put("/{id}") {
                val taskId = call.parameters["id"]?.toIntOrNull()
                if (taskId != null) {
                    val taskRequest = call.receive<Task>()
                    val updatedTask = todoService.updateTask(
                        taskId, taskRequest.title, taskRequest.description, taskRequest.completed
                    )
                    if (updatedTask != null) {
                        call.respond(updatedTask)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Task not found")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid task ID")
                }
            }
            delete("/id") {
                val taskId = call.parameters["id"]?.toIntOrNull()
                if (taskId != null) {
                    val deleted = todoService.deleteTask(taskId)
                    if (deleted == true) {
                        call.respond(HttpStatusCode.NoContent)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Task not found")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid task ID")
                }
            }
        }
    }
}
