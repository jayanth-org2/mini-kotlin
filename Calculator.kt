class Calculator {
    private var lastResult: Double = 0.0
    
    fun add(a: Double, b: Double): Double {
        lastResult = a + b
        return lastResult
    }
    
    fun subtract(a: Double, b: Double): Double {
        lastResult = a - b
        return lastResult
    }
    
    fun multiply(a: Double, b: Double): Double {
        lastResult = a * b
        return lastResult
    }
    
    fun divide(a: Double, b: Double): Double {
        if (b == 0.0) {
            throw IllegalArgumentException("Cannot divide by zero")
        }
        lastResult = a / b
        return lastResult
    }
    
    fun power(base: Double, exponent: Double): Double {
        lastResult = Math.pow(base, exponent)
        return lastResult
    }
    
    fun getLastResult(): Double {
        return lastResult
    }
    
    fun clear() {
        lastResult = 0.0
    }
} 