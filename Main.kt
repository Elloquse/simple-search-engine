package search
import java.io.File

const val menu: String = """=== Menu ===
1. Find a person
2. Print all people
0. Exit
"""

enum class Strategy {
    ALL,
    ANY,
    NONE
}

fun main(args: Array<String>) {
    val listOfPeople: List<String> = File(args[1]).readLines()
    menu@ while(true) {
        println(menu)
        when (readln().toInt()) {
            1 -> findPerson(listOfPeople)
            2 -> printAllPeople(listOfPeople)
            0 -> break@menu
            else -> println("Incorrect option! Try again.")
        }
    }
}

fun findPerson(file: List<String>) {
    println("Select a matching strategy: ALL, ANY, NONE")
    val matchStrategy: String = readln().uppercase()
    println()

    println("Enter a name or email to search all suitable people.")
    val personToSearch: List<String> = readln().lowercase().split(" ")
    println()

    fun findPersonAll() {
        var invertedIndex: Map<String, Int> = file.associate { it to file.indexOf(it) }
        invertedIndex = invertedIndex.filter { it.key.lowercase().split(" ").containsAll(personToSearch) }

        for (name in invertedIndex.keys) {
            println(name)
        }
        println("")
    }

    fun findPersonAny() {
        val invertedIndex: MutableMap<String, MutableList<Int>> = mutableMapOf<String, MutableList<Int>>()
        file.forEach { e -> e.split(" ").forEach { if (!invertedIndex.containsKey(it)) invertedIndex[it] = mutableListOf(file.indexOf(e)) else invertedIndex.getValue(it).add(file.indexOf(e)) }}

        val linesToPrint = invertedIndex.filter { it.key.lowercase() in personToSearch}.values.toSet().flatten()

        for (line in linesToPrint) {
            println(file[line])
        }
        println("")
    }

    fun findPersonNone() {
        val invertedIndex: MutableMap<String, MutableList<Int>> = mutableMapOf<String, MutableList<Int>>()
        file.forEach { e -> e.split(" ").forEach { if (!invertedIndex.containsKey(it)) invertedIndex[it] = mutableListOf(file.indexOf(e)) else invertedIndex.getValue(it).add(file.indexOf(e)) }}

        val linesNotToPrint = invertedIndex.filter { it.key.lowercase() in personToSearch}.values.toSet().flatten()
        val linesToPrint = (file.indices).toMutableList<Int>().filter { !linesNotToPrint.contains(it) }

        for (line in linesToPrint) {
            println(file[line])
        }
        println("")
    }

    when (matchStrategy) {
        Strategy.ALL.name -> findPersonAll()
        Strategy.ANY.name -> findPersonAny()
        Strategy.NONE.name -> findPersonNone()
    }
}

fun printAllPeople(listOfPeople: List<String>) {
    println()
    println("=== List of people ===")
    for (person in listOfPeople) {
        println(person)
    }
    println("")
}
