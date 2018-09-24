import kotlin.properties.Delegates

// ----------------getter setter-----------------
class Person (
    var firstName: String? = null,
    var lastName: String? = null) {
    val fullName: String
        get() {
            return firstName + " " + lastName
        }

    var otherName: String = ""
        private set

}

var itemList: ArrayList<String> = ArrayList<String>()
    set(value) {
        // field is the default collection variable
        field = value
        // Dp something else
    }

// ---------------This and inner class------------------

class Parent {
    var firstName = ""
    var child = Child()

    fun setFirst(firstName : String) {
        this.firstName = firstName
    }

    inner class Child {
        var firstName = ""

        fun printPercentage() {
            println("Child ${this.firstName} with parent ${this@Parent.firstName}")
        }
    }
}

// extension function
fun String.lastChar(): Char = this.get(this.length - 1)

// ---------------------------late init-----------------------------
class Bloke(var firstName: String, var lastName: String) {

    // if you know that you will be initialise it in init block or before it use, you can use lateinit to avoid using safe call(the ?).
    lateinit var fullName: String

    init {
        fullName = firstName + " " + lastName
    }

    fun printFullName() {

        // .isInitialized is a property of lateinit
        if (!this::fullName.isInitialized) {
            fullName = firstName + " " + lastName
        }
        println(fullName)
    }
}

// --------------------------Delegated Properties-Lazy-Observable-Mapped-------------------------------

// data class Course(var className: String)

// this lazy property will be created at the first time it is called and will be cached so the next time it's called it won't regenerate
// val scienceCourse: Course by lazy {
//     Course("Science")
// }

data class Course(val map: Map<String, Any?>) {
    val room: String by map
    val teacher: String by map
}

data class Course2(var className: String) {
    val courseDescription: String by lazy {
        "Course ${className} taught by $teacherName"
    }

    private lateinit var teacherName: String

    var room : String by Delegates.observable("No room") {
        property, oldValue, newValue -> println("New value is $newValue")
    }

    fun setTeacher(teacher: String) {
        teacherName = teacher
    }
}

fun main(args: Array<String>) {

    // ----------------getter setter-----------------
    val person = Person("Same", "Smith")
    println("Person: ${person.firstName} ${person.lastName}")

    // ---------------This and inner class------------------
    val parent = Parent()
    parent.firstName = "Sam"
    parent.child.firstName = "Suzy"
    parent.child.printPercentage()

    println("Hello there ${"Sammy".lastChar()}")

    // --------------------------Delegated Properties-Lazy-Observable-Mapped-------------------------------
    val course = Course(mapOf("room" to "Room 1", "teacher" to "Ms Price"))

    println(course)
    println(course.teacher)

    // course 2 - Observable example
    val course2 = Course2("Math")
    course2.setTeacher("Dr. Malcomm")
    course2.room = "Library"
    println("Course Description ${course2.courseDescription}")
    println("Course is ${course2}")

}