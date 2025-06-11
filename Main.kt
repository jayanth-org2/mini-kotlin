// Main function and demonstration functions

fun main() {
    println("=== Kotlin Project Demo ===\n")
    
    // Demonstrate Person class
    demonstratePersonClass()
    
    // Demonstrate Calculator class
    demonstrateCalculator()
    
    // Demonstrate utility functions
    demonstrateUtilityFunctions()
    
    // Demonstrate standalone functions
    demonstrateStandaloneFunctions()
}

fun demonstratePersonClass() {
    println("--- Person Class Demo ---")
    val person1 = Person("Alice Johnson", 25, "alice@example.com")
    val person2 = Person("Bob Smith", 17, "bob@test.org")
    
    println("Person 1: ${person1.getDisplayName()}")
    println("Is adult: ${person1.isAdult()}")
    println("Email domain: ${person1.getEmailDomain()}")
    println("Full info: $person1")
    
    println("\nPerson 2: ${person2.getDisplayName()}")
    println("Is adult: ${person2.isAdult()}")
    println("Email domain: ${person2.getEmailDomain()}")
    println()
}

fun demonstrateCalculator() {
    println("--- Calculator Class Demo ---")
    val calc = Calculator()
    
    println("10 + 5 = ${calc.add(10.0, 5.0)}")
    println("Last result: ${calc.getLastResult()}")
    
    println("20 - 8 = ${calc.subtract(20.0, 8.0)}")
    println("6 * 7 = ${calc.multiply(6.0, 7.0)}")
    println("15 / 3 = ${calc.divide(15.0, 3.0)}")
    println("2^8 = ${calc.power(2.0, 8.0)}")
    
    calc.clear()
    println("After clear: ${calc.getLastResult()}")
    println()
}

fun demonstrateUtilityFunctions() {
    println("--- Utility Functions Demo ---")
    println("Currency format: ${formatCurrency(1234.56, "EUR")}")
    println("Email validation: ${isValidEmail("test@example.com")}")
    println("Random string: ${generateRandomString(8)}")
    println("Factorial of 5: ${factorial(5)}")
    println("Is 17 prime? ${isPrime(17)}")
    println("Reverse 'Hello': ${reverseString("Hello")}")
    println("Word count in 'Hello world from Kotlin': ${countWords("Hello world from Kotlin")}")
    println()
}

fun demonstrateStandaloneFunctions() {
    println("--- Standalone Functions Demo ---")
    val numbers = listOf(1, 2, 3, 4, 5)
    println("Sum of $numbers = ${calculateSum(numbers)}")
    println("Average of $numbers = ${calculateAverage(numbers)}")
    
    val text = "Hello World"
    println("'$text' has ${countVowels(text)} vowels")
    println("Fibonacci sequence (10 terms): ${generateFibonacci(10)}")
}

fun calculateSum(numbers: List<Int>): Int {
    return numbers.sum()
}

fun calculateAverage(numbers: List<Int>): Double {
    return if (numbers.isEmpty()) 0.0 else numbers.average()
}

fun countVowels(text: String): Int {
    val vowels = "aeiouAEIOU"
    return text.count { it in vowels }
}

fun generateFibonacci(n: Int): List<Long> {
    if (n <= 0) return emptyList()
    if (n == 1) return listOf(0)
    if (n == 2) return listOf(0, 1)
    
    val fibonacci = mutableListOf(0L, 1L)
    for (i in 2 until n) {
        fibonacci.add(fibonacci[i - 1] + fibonacci[i - 2])
    }
    return fibonacci
} 