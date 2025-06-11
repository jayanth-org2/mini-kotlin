// Various enum classes demonstrating different features

enum class Priority {
    LOW, MEDIUM, HIGH, CRITICAL
}

enum class Status(val displayName: String, val isActive: Boolean) {
    PENDING("Pending Review", true),
    IN_PROGRESS("In Progress", true),
    COMPLETED("Completed", false),
    CANCELLED("Cancelled", false),
    ON_HOLD("On Hold", true);
    
    fun canTransitionTo(newStatus: Status): Boolean {
        return when (this) {
            PENDING -> newStatus in listOf(IN_PROGRESS, CANCELLED)
            IN_PROGRESS -> newStatus in listOf(COMPLETED, ON_HOLD, CANCELLED)
            ON_HOLD -> newStatus in listOf(IN_PROGRESS, CANCELLED)
            COMPLETED, CANCELLED -> false
        }
    }
}

enum class Color(val hex: String, val rgb: Triple<Int, Int, Int>) {
    RED("#FF0000", Triple(255, 0, 0)),
    GREEN("#00FF00", Triple(0, 255, 0)),
    BLUE("#0000FF", Triple(0, 0, 255)),
    YELLOW("#FFFF00", Triple(255, 255, 0)),
    PURPLE("#800080", Triple(128, 0, 128)),
    ORANGE("#FFA500", Triple(255, 165, 0)),
    BLACK("#000000", Triple(0, 0, 0)),
    WHITE("#FFFFFF", Triple(255, 255, 255));
    
    fun isDark(): Boolean {
        val (r, g, b) = rgb
        val brightness = (r * 299 + g * 587 + b * 114) / 1000
        return brightness < 128
    }
    
    companion object {
        fun fromHex(hex: String): Color? {
            return values().find { it.hex.equals(hex, ignoreCase = true) }
        }
    }
}

enum class Planet(val mass: Double, val radius: Double) {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS(4.869e+24, 6.0518e6),
    EARTH(5.976e+24, 6.37814e6),
    MARS(6.421e+23, 3.3972e6),
    JUPITER(1.9e+27, 7.1492e7),
    SATURN(5.688e+26, 6.0268e7),
    URANUS(8.686e+25, 2.5559e7),
    NEPTUNE(1.024e+26, 2.4746e7);
    
    private val G = 6.67300E-11
    
    fun surfaceGravity(): Double {
        return G * mass / (radius * radius)
    }
    
    fun surfaceWeight(otherMass: Double): Double {
        return otherMass * surfaceGravity()
    }
}

enum class Direction(val degrees: Int) {
    NORTH(0),
    NORTHEAST(45),
    EAST(90),
    SOUTHEAST(135),
    SOUTH(180),
    SOUTHWEST(225),
    WEST(270),
    NORTHWEST(315);
    
    fun opposite(): Direction {
        val oppositeDegrees = (degrees + 180) % 360
        return values().find { it.degrees == oppositeDegrees } ?: NORTH
    }
    
    fun turnRight(): Direction {
        val newDegrees = (degrees + 90) % 360
        return values().find { it.degrees == newDegrees } ?: NORTH
    }
    
    fun turnLeft(): Direction {
        val newDegrees = (degrees - 90 + 360) % 360
        return values().find { it.degrees == newDegrees } ?: NORTH
    }
} 