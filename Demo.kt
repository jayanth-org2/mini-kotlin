// Comprehensive demo of all Kotlin features

fun main() {
    println("=== Extended Kotlin Features Demo ===\n")
    
    demoInterfaces()
    demoEnums()
    demoSealedClasses()
    demoExtensions()
    demoGenerics()
    demoTypeBuilders()
}

fun demoInterfaces() {
    println("--- Interface Demo ---")
    
    val shapes: List<Shape> = listOf(
        Circle(5.0),
        Rectangle(4.0, 6.0),
        Triangle(3.0, 4.0, 5.0)
    )
    
    shapes.forEach { shape ->
        println(shape.describe())
        when (shape) {
            is Circle -> println("  Diameter: ${shape.getDiameter()}")
            is Rectangle -> println("  Is Square: ${shape.isSquare()}")
            is Triangle -> println("  Is Equilateral: ${shape.isEquilateral()}")
        }
    }
    println()
}

fun demoEnums() {
    println("--- Enum Demo ---")
    
    // Priority enum
    val task1Priority = Priority.HIGH
    println("Task priority: $task1Priority")
    
    // Status enum with properties and methods
    val currentStatus = Status.PENDING
    println("Current status: ${currentStatus.displayName} (Active: ${currentStatus.isActive})")
    println("Can transition to IN_PROGRESS: ${currentStatus.canTransitionTo(Status.IN_PROGRESS)}")
    
    // Color enum with complex properties
    val favoriteColor = Color.BLUE
    println("Favorite color: $favoriteColor (${favoriteColor.hex})")
    println("RGB: ${favoriteColor.rgb}")
    println("Is dark: ${favoriteColor.isDark()}")
    
    val colorFromHex = Color.fromHex("#FF0000")
    println("Color from hex #FF0000: $colorFromHex")
    
    // Planet enum with calculations
    val earthWeight = 70.0 // kg
    println("Weight on different planets for ${earthWeight}kg person:")
    Planet.values().take(4).forEach { planet ->
        val weight = planet.surfaceWeight(earthWeight)
        println("  ${planet.name}: ${"%.2f".format(weight)} kg")
    }
    
    // Direction enum
    val direction = Direction.NORTH
    println("Direction: $direction (${direction.degrees}°)")
    println("Opposite: ${direction.opposite()}")
    println("Turn right: ${direction.turnRight()}")
    println("Turn left: ${direction.turnLeft()}")
    println()
}

fun demoSealedClasses() {
    println("--- Sealed Classes Demo ---")
    
    // Result sealed class
    val results: List<Result<String>> = listOf(
        Result.Success("Data loaded successfully"),
        Result.Error(Exception("Network error")),
        Result.Loading
    )
    
    results.forEach { result ->
        val message = result.fold(
            onSuccess = { "✓ Success: $it" },
            onError = { "✗ Error: ${it.message}" },
            onLoading = { "⏳ Loading..." }
        )
        println(message)
    }
    
    // Expression sealed class
    val expression = Expression.Add(
        Expression.Multiply(Expression.Number(2.0), Expression.Variable("x")),
        Expression.Number(5.0)
    )
    
    val variables = mapOf("x" to 3.0)
    val result = expression.evaluate(variables)
    println("Expression (2 * x + 5) where x=3: $result")
    
    // UI Event handling
    val events = listOf(
        UIEvent.OnStart,
        UIEvent.OnClick("button1"),
        UIEvent.OnTextChanged("Hello", "input1"),
        UIEvent.OnError("Validation failed")
    )
    
    events.forEach { event ->
        when (event) {
            is UIEvent.OnStart -> println("App started")
            is UIEvent.OnClick -> println("Button clicked: ${event.elementId}")
            is UIEvent.OnTextChanged -> println("Text changed in ${event.fieldId}: '${event.text}'")
            is UIEvent.OnError -> println("Error occurred: ${event.error}")
            else -> println("Other event: $event")
        }
    }
    
    // Data classes
    val user = User(1L, "john_doe", "john@example.com")
    val task = Task(
        id = 1L,
        title = "Complete project",
        description = "Finish the Kotlin demo project",
        priority = Priority.HIGH,
        status = Status.IN_PROGRESS,
        assignedTo = user,
        dueDate = System.currentTimeMillis() + 86400000 // 1 day from now
    )
    
    println("User: ${user.getDisplayName()}")
    println("Task: ${task.title}")
    println("Can be completed by user: ${task.canBeCompletedBy(user)}")
    println("Is overdue: ${task.isOverdue()}")
    
    val point1 = Point(0.0, 0.0)
    val point2 = Point(3.0, 4.0)
    println("Distance between points: ${point1.distanceTo(point2)}")
    println("Point addition: ${point1 + point2}")
    println()
}

fun demoExtensions() {
    println("--- Extension Functions Demo ---")
    
    // String extensions
    val email = "test@example.com"
    println("'$email' is valid email: ${email.isValidEmail()}")
    
    val text = "hello world kotlin"
    println("Capitalized: ${text.capitalizeWords()}")
    println("Word count: ${text.wordCount}")
    println("Truncated: ${text.truncate(10)}")
    
    // Number extensions
    val number = 5
    println("$number is even: ${number.isEven()}")
    println("$number factorial: ${number.factorial()}")
    println("$number squared: ${number.squared}")
    
    val decimal = 3.14159
    println("$decimal rounded to 2 decimals: ${decimal.roundTo(2)}")
    
    // List extensions
    val numbers = listOf(1, 2, 3, 4, 5, 6)
    println("Second element: ${numbers.secondOrNull()}")
    println("Penultimate element: ${numbers.penultimate()}")
    println("Average: ${numbers.average()}")
    println("Chunked by 2: ${numbers.chunked(2)}")
    
    // Infix functions
    println("'hello' matches '[a-z]+': ${"hello" matches "[a-z]+"}")
    println("5 is in [1,2,3,4,5]: ${5 isIn listOf(1, 2, 3, 4, 5)}")
    println("2 pow 3: ${2 pow 3}")
    
    // Date extensions
    val timestamp = System.currentTimeMillis()
    println("Current time: ${timestamp.toDateString()}")
    println("Is in past: ${(timestamp - 1000).isInPast}")
    println()
}

fun demoGenerics() {
    println("--- Generics Demo ---")
    
    // Generic repository
    val userRepo = InMemoryRepository<User, Long> { it.id }
    val user1 = User(1L, "alice", "alice@test.com")
    val user2 = User(2L, "bob", "bob@test.com")
    
    userRepo.save(user1)
    userRepo.save(user2)
    
    println("All users: ${userRepo.findAll().map { it.username }}")
    println("User with ID 1: ${userRepo.findById(1L)?.username}")
    
    // Generic cache
    val cache = Cache<String, Int>(maxSize = 3)
    cache.put("one", 1)
    cache.put("two", 2)
    cache.put("three", 3)
    
    println("Cache size: ${cache.size()}")
    println("Get 'two': ${cache.get("two")}")
    
    // Generic tree
    val tree = Tree.Node(
        1,
        Tree.Node(2, Tree.Empty, Tree.Empty),
        Tree.Node(3, Tree.Empty, Tree.Empty)
    )
    
    val stringTree = tree.map { it.toString() }
    val sum = tree.fold(0) { acc, value -> acc + value }
    println("Tree sum: $sum")
    
    // List builder
    val builtList = buildList<String> {
        add("Hello")
        add("World")
        addAll(listOf("from", "Kotlin"))
    }
    println("Built list: $builtList")
    
    // Validator
    val emailValidator = object : Validator<String> {
        override fun validate(value: String): ValidationResult {
            return if (value.isValidEmail()) {
                ValidationResult.valid()
            } else {
                ValidationResult.invalid("Invalid email format")
            }
        }
    }
    
    val lengthValidator = object : Validator<String> {
        override fun validate(value: String): ValidationResult {
            return if (value.length >= 5) {
                ValidationResult.valid()
            } else {
                ValidationResult.invalid("Email must be at least 5 characters")
            }
        }
    }
    
    val compositeValidator = CompositeValidator<String>()
        .add(emailValidator)
        .add(lengthValidator)
    
    val validationResult = compositeValidator.validate("test@example.com")
    println("Validation result: ${validationResult.isValid}")
    
    // Event bus
    val eventBus = EventBus<String>()
    val listener = object : EventListener<String> {
        override fun onEvent(event: String) {
            println("Received event: $event")
        }
    }
    
    eventBus.subscribe(listener)
    eventBus.publish("Hello Event System!")
    println()
}

fun demoTypeBuilders() {
    println("--- Type-Safe Builders Demo ---")
    
    val htmlDocument = html {
        head {
            title("My Kotlin Demo Page")
        }
        body {
            div {
                p("Welcome to the Kotlin features demonstration!")
                p("This page was built using a type-safe DSL.")
            }
            p("Thank you for exploring Kotlin with us!")
        }
    }
    
    println("Generated HTML:")
    println(htmlDocument.toString())
    println()
} 