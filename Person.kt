data class Person(
    val name: String,
    val age: Int,
    val email: String
) {
    fun getDisplayName(): String {
        return "$name (Age: $age)"
    }
    
    fun isAdult(): Boolean {
        return age >= 18
    }
    
    fun getEmailDomain(): String {
        return email.substringAfter("@")
    }
    
    override fun toString(): String {
        return "Person(name='$name', age=$age, email='$email')"
    }
} 