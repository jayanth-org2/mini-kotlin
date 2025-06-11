// Utility functions without classes

fun formatCurrency(amount: Double, currency: String = "USD"): String {
    return "$currency ${"%.2f".format(amount)}"
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return emailRegex.matches(email)
}

fun generateRandomString(length: Int): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { chars.random() }
        .joinToString("")
}

fun factorial(n: Int): Long {
    if (n < 0) throw IllegalArgumentException("Factorial is not defined for negative numbers")
    if (n == 0 || n == 1) return 1
    return n * factorial(n - 1)
}

fun isPrime(number: Int): Boolean {
    if (number < 2) return false
    if (number == 2) return true
    if (number % 2 == 0) return false
    
    for (i in 3..Math.sqrt(number.toDouble()).toInt() step 2) {
        if (number % i == 0) return false
    }
    return true
}

fun reverseString(input: String): String {
    return input.reversed()
}

fun countWords(text: String): Int {
    return text.trim().split("\\s+".toRegex()).size
} 