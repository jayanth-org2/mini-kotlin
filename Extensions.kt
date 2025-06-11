// Extension functions and properties

// String extensions
fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
}

fun String.capitalizeWords(): String {
    return this.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}

fun String.removeWhitespace(): String = this.replace("\\s".toRegex(), "")

val String.wordCount: Int
    get() = this.trim().split("\\s+".toRegex()).size

fun String.truncate(maxLength: Int, suffix: String = "..."): String {
    return if (this.length <= maxLength) this
    else this.take(maxLength - suffix.length) + suffix
}

// List extensions
fun <T> List<T>.second(): T {
    if (size < 2) throw NoSuchElementException("List has less than 2 elements")
    return this[1]
}

fun <T> List<T>.secondOrNull(): T? = if (size >= 2) this[1] else null

fun <T> List<T>.penultimate(): T {
    if (size < 2) throw NoSuchElementException("List has less than 2 elements")
    return this[size - 2]
}

fun List<Int>.average(): Double = if (isEmpty()) 0.0 else sum().toDouble() / size

fun <T> List<T>.chunked(size: Int): List<List<T>> {
    return if (size <= 0) throw IllegalArgumentException("Size must be positive")
    else (0 until this.size step size).map { i ->
        this.subList(i, minOf(i + size, this.size))
    }
}

// Number extensions
fun Int.isEven(): Boolean = this % 2 == 0
fun Int.isOdd(): Boolean = this % 2 != 0

fun Int.factorial(): Long {
    if (this < 0) throw IllegalArgumentException("Factorial is not defined for negative numbers")
    return if (this <= 1) 1 else this * (this - 1).factorial()
}

fun Double.roundTo(decimals: Int): Double {
    val multiplier = Math.pow(10.0, decimals.toDouble())
    return Math.round(this * multiplier) / multiplier
}

val Int.squared: Int get() = this * this
val Double.squared: Double get() = this * this

// Collection extensions
fun <T> Collection<T>.isNotEmpty(): Boolean = !isEmpty()

fun <K, V> Map<K, V>.getOrThrow(key: K): V {
    return this[key] ?: throw NoSuchElementException("Key $key not found in map")
}

// Date/Time extensions (using Long as timestamp)
fun Long.toDateString(): String {
    val date = java.util.Date(this)
    val formatter = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return formatter.format(date)
}

val Long.isInPast: Boolean get() = this < System.currentTimeMillis()
val Long.isInFuture: Boolean get() = this > System.currentTimeMillis()

// Boolean extensions
fun Boolean.toInt(): Int = if (this) 1 else 0
fun Boolean.toString(trueValue: String, falseValue: String): String = if (this) trueValue else falseValue

// Generic extensions
inline fun <T> T.applyIf(condition: Boolean, block: T.() -> T): T {
    return if (condition) block() else this
}

inline fun <T> T.also(block: (T) -> Unit): T {
    block(this)
    return this
}

// Pair extensions
operator fun <T> Pair<T, T>.plus(other: Pair<T, T>): Pair<T, T> where T : Number {
    return when (first) {
        is Int -> Pair((first as Int + other.first as Int) as T, (second as Int + other.second as Int) as T)
        is Double -> Pair((first as Double + other.first as Double) as T, (second as Double + other.second as Double) as T)
        else -> throw UnsupportedOperationException("Unsupported number type")
    }
}

// Scope functions demonstration
inline fun <T, R> T.letIf(condition: Boolean, block: (T) -> R): R? {
    return if (condition) block(this) else null
}

// Custom infix functions
infix fun String.matches(regex: String): Boolean = this.matches(regex.toRegex())
infix fun <T> T.isIn(collection: Collection<T>): Boolean = collection.contains(this)
infix fun Int.pow(exponent: Int): Int = Math.pow(this.toDouble(), exponent.toDouble()).toInt() 