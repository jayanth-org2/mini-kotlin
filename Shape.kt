// Interface definition and implementations

interface Shape {
    fun area(): Double
    fun perimeter(): Double
    fun getShapeType(): String
    
    // Default implementation (interface method with body)
    fun describe(): String {
        return "${getShapeType()}: Area = ${"%.2f".format(area())}, Perimeter = ${"%.2f".format(perimeter())}"
    }
}

class Circle(private val radius: Double) : Shape {
    override fun area(): Double = Math.PI * radius * radius
    
    override fun perimeter(): Double = 2 * Math.PI * radius
    
    override fun getShapeType(): String = "Circle"
    
    fun getDiameter(): Double = 2 * radius
}

class Rectangle(private val width: Double, private val height: Double) : Shape {
    override fun area(): Double = width * height
    
    override fun perimeter(): Double = 2 * (width + height)
    
    override fun getShapeType(): String = "Rectangle"
    
    fun isSquare(): Boolean = width == height
}

class Triangle(private val a: Double, private val b: Double, private val c: Double) : Shape {
    init {
        require(a + b > c && b + c > a && a + c > b) { "Invalid triangle sides" }
    }
    
    override fun area(): Double {
        val s = perimeter() / 2
        return Math.sqrt(s * (s - a) * (s - b) * (s - c))
    }
    
    override fun perimeter(): Double = a + b + c
    
    override fun getShapeType(): String = "Triangle"
    
    fun isEquilateral(): Boolean = a == b && b == c
} 