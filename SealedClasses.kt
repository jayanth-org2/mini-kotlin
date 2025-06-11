// Sealed classes for type-safe hierarchies

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
    
    fun isSuccess(): Boolean = this is Success
    fun isError(): Boolean = this is Error
    fun isLoading(): Boolean = this is Loading
    
    inline fun <R> fold(
        onSuccess: (T) -> R,
        onError: (Throwable) -> R,
        onLoading: () -> R
    ): R = when (this) {
        is Success -> onSuccess(data)
        is Error -> onError(exception)
        is Loading -> onLoading()
    }
}

sealed class NetworkResponse {
    data class Success(val data: String, val statusCode: Int) : NetworkResponse()
    data class ClientError(val message: String, val statusCode: Int) : NetworkResponse()
    data class ServerError(val message: String, val statusCode: Int) : NetworkResponse()
    object NetworkError : NetworkResponse()
    object Timeout : NetworkResponse()
    
    fun isSuccessful(): Boolean = this is Success
    
    fun getStatusCode(): Int? = when (this) {
        is Success -> statusCode
        is ClientError -> statusCode
        is ServerError -> statusCode
        else -> null
    }
}

sealed class Expression {
    data class Number(val value: Double) : Expression()
    data class Variable(val name: String) : Expression()
    data class Add(val left: Expression, val right: Expression) : Expression()
    data class Multiply(val left: Expression, val right: Expression) : Expression()
    data class Subtract(val left: Expression, val right: Expression) : Expression()
    data class Divide(val left: Expression, val right: Expression) : Expression()
    
    fun evaluate(variables: Map<String, Double> = emptyMap()): Double = when (this) {
        is Number -> value
        is Variable -> variables[name] ?: throw IllegalArgumentException("Variable $name not found")
        is Add -> left.evaluate(variables) + right.evaluate(variables)
        is Multiply -> left.evaluate(variables) * right.evaluate(variables)
        is Subtract -> left.evaluate(variables) - right.evaluate(variables)
        is Divide -> {
            val rightValue = right.evaluate(variables)
            if (rightValue == 0.0) throw ArithmeticException("Division by zero")
            left.evaluate(variables) / rightValue
        }
    }
}

sealed class UIEvent {
    object OnStart : UIEvent()
    object OnStop : UIEvent()
    object OnResume : UIEvent()
    object OnPause : UIEvent()
    data class OnClick(val elementId: String) : UIEvent()
    data class OnTextChanged(val text: String, val fieldId: String) : UIEvent()
    data class OnError(val error: String) : UIEvent()
}

// Data classes for various entities
data class User(
    val id: Long,
    val username: String,
    val email: String,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun getDisplayName(): String = username.replaceFirstChar { it.uppercase() }
}

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val priority: Priority,
    val status: Status,
    val assignedTo: User?,
    val dueDate: Long?
) {
    fun isOverdue(): Boolean {
        return dueDate?.let { it < System.currentTimeMillis() } ?: false
    }
    
    fun canBeCompletedBy(user: User): Boolean {
        return assignedTo?.id == user.id && status.canTransitionTo(Status.COMPLETED)
    }
}

data class Point(val x: Double, val y: Double) {
    fun distanceTo(other: Point): Double {
        val dx = x - other.x
        val dy = y - other.y
        return Math.sqrt(dx * dx + dy * dy)
    }
    
    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
    operator fun minus(other: Point): Point = Point(x - other.x, y - other.y)
}

data class Address(
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String = "USA"
) {
    fun getFullAddress(): String {
        return "$street, $city, $state $zipCode, $country"
    }
} 