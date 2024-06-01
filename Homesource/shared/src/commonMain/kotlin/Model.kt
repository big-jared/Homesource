import kotlinx.serialization.Serializable

enum class Role {
    Parent, Student,
}

@Serializable
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val authToken: String,
    val password: String = "",
    val confirmPassword: String = "",
)