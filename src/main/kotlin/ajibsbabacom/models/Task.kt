package ajibsbabacom.models

import kotlinx.serialization.Serializable

@Serializable
data class Task(val id: Int, val title: String, val description: String, var completed: Boolean)