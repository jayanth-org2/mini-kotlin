// Generic classes and functions

// Generic repository pattern
interface Repository<T, ID> {
    fun save(entity: T): T
    fun findById(id: ID): T?
    fun findAll(): List<T>
    fun delete(id: ID): Boolean
    fun update(entity: T): T?
}

class InMemoryRepository<T, ID>(
    private val idExtractor: (T) -> ID
) : Repository<T, ID> {
    private val storage = mutableMapOf<ID, T>()
    
    override fun save(entity: T): T {
        val id = idExtractor(entity)
        storage[id] = entity
        return entity
    }
    
    override fun findById(id: ID): T? = storage[id]
    
    override fun findAll(): List<T> = storage.values.toList()
    
    override fun delete(id: ID): Boolean = storage.remove(id) != null
    
    override fun update(entity: T): T? {
        val id = idExtractor(entity)
        return if (storage.containsKey(id)) {
            storage[id] = entity
            entity
        } else null
    }
}

// Generic cache implementation
class Cache<K, V>(private val maxSize: Int = 100) {
    private val cache = LinkedHashMap<K, V>(maxSize, 0.75f, true)
    
    fun put(key: K, value: V): V? {
        val previous = cache.put(key, value)
        if (cache.size > maxSize) {
            val eldest = cache.entries.first()
            cache.remove(eldest.key)
        }
        return previous
    }
    
    fun get(key: K): V? = cache[key]
    
    fun remove(key: K): V? = cache.remove(key)
    
    fun clear() = cache.clear()
    
    fun size(): Int = cache.size
    
    fun containsKey(key: K): Boolean = cache.containsKey(key)
}

// Generic tree structure
sealed class Tree<out T> {
    object Empty : Tree<Nothing>()
    data class Node<T>(val value: T, val left: Tree<T>, val right: Tree<T>) : Tree<T>()
    
    fun <R> map(transform: (T) -> R): Tree<R> = when (this) {
        is Empty -> Empty
        is Node -> Node(transform(value), left.map(transform), right.map(transform))
    }
    
    fun fold(initial: Int, operation: (Int, T) -> Int): Int = when (this) {
        is Empty -> initial
        is Node -> {
            val leftResult = left.fold(initial, operation)
            val nodeResult = operation(leftResult, value)
            right.fold(nodeResult, operation)
        }
    }
}

// Generic builder pattern
class ListBuilder<T> {
    private val items = mutableListOf<T>()
    
    fun add(item: T) {
        items.add(item)
    }
    
    fun addAll(items: Collection<T>) {
        this.items.addAll(items)
    }
    
    fun remove(item: T): Boolean = items.remove(item)
    
    fun build(): List<T> = items.toList()
}

fun <T> buildList(builder: ListBuilder<T>.() -> Unit): List<T> {
    return ListBuilder<T>().apply(builder).build()
}

// Generic validator
interface Validator<T> {
    fun validate(value: T): ValidationResult
}

data class ValidationResult(
    val isValid: Boolean,
    val errors: List<String> = emptyList()
) {
    companion object {
        fun valid() = ValidationResult(true)
        fun invalid(vararg errors: String) = ValidationResult(false, errors.toList())
    }
}

class CompositeValidator<T> : Validator<T> {
    private val validators = mutableListOf<Validator<T>>()
    
    fun add(validator: Validator<T>): CompositeValidator<T> {
        validators.add(validator)
        return this
    }
    
    override fun validate(value: T): ValidationResult {
        val allErrors = mutableListOf<String>()
        
        validators.forEach { validator ->
            val result = validator.validate(value)
            if (!result.isValid) {
                allErrors.addAll(result.errors)
            }
        }
        
        return if (allErrors.isEmpty()) {
            ValidationResult.valid()
        } else {
            ValidationResult.invalid(*allErrors.toTypedArray())
        }
    }
}

// Generic event system
interface EventListener<T> {
    fun onEvent(event: T)
}

class EventBus<T> {
    private val listeners = mutableListOf<EventListener<T>>()
    
    fun subscribe(listener: EventListener<T>) {
        listeners.add(listener)
    }
    
    fun unsubscribe(listener: EventListener<T>) {
        listeners.remove(listener)
    }
    
    fun publish(event: T) {
        listeners.forEach { it.onEvent(event) }
    }
    
    fun clear() {
        listeners.clear()
    }
}

// Generic utility functions
fun <T> T?.orElse(default: T): T = this ?: default

fun <T, R> T?.mapNotNull(transform: (T) -> R?): R? = this?.let(transform)

fun <T> List<T>.partitionBy(predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
    val trueList = mutableListOf<T>()
    val falseList = mutableListOf<T>()
    
    forEach { item ->
        if (predicate(item)) trueList.add(item) else falseList.add(item)
    }
    
    return Pair(trueList, falseList)
}

// Type-safe builder for HTML-like structure
@DslMarker
annotation class HtmlDsl

@HtmlDsl
class Html {
    private val children = mutableListOf<Element>()
    
    fun head(init: Head.() -> Unit) {
        children.add(Head().apply(init))
    }
    
    fun body(init: Body.() -> Unit) {
        children.add(Body().apply(init))
    }
    
    override fun toString(): String = children.joinToString("\n")
}

@HtmlDsl
abstract class Element(val name: String) {
    protected val children = mutableListOf<Element>()
    protected val attributes = mutableMapOf<String, String>()
    
    fun attribute(name: String, value: String) {
        attributes[name] = value
    }
    
    override fun toString(): String {
        val attrs = if (attributes.isEmpty()) "" else " " + attributes.map { "${it.key}=\"${it.value}\"" }.joinToString(" ")
        val content = if (children.isEmpty()) "" else "\n${children.joinToString("\n").prependIndent("  ")}\n"
        return "<$name$attrs>$content</$name>"
    }
}

@HtmlDsl
class Head : Element("head") {
    fun title(text: String) {
        children.add(Title(text))
    }
}

@HtmlDsl
class Body : Element("body") {
    fun div(init: Div.() -> Unit) {
        children.add(Div().apply(init))
    }
    
    fun p(text: String) {
        children.add(P(text))
    }
}

@HtmlDsl
class Div : Element("div") {
    fun p(text: String) {
        children.add(P(text))
    }
}

class Title(private val text: String) : Element("title") {
    override fun toString(): String = "<title>$text</title>"
}

class P(private val text: String) : Element("p") {
    override fun toString(): String = "<p>$text</p>"
}

fun html(init: Html.() -> Unit): Html = Html().apply(init) 