package com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic

class OrderedVariables(
    variableCount: Int,
    variablesNames: List<String>? = null
) : Iterable<Pair<String, Boolean>> {

    private val names = variablesNames ?: List(variableCount) { index -> "x${index + 1}" }
    private val values = MutableList(variableCount) { false }

    operator fun get(name: String): Boolean {
        val index = names.indexOf(name)
        if (index == -1) throw IllegalArgumentException("Variable $name not found")
        return values[index]
    }

    operator fun set(name: String, value: Boolean) {
        val index = names.indexOf(name)
        if (index == -1) throw IllegalArgumentException("Variable $name not found")
        values[index] = value
    }

    fun getByIndex(index: Int): Pair<String, Boolean> {
        return names[index] to values[index]
    }

    fun setByIndex(index: Int, value: Boolean) {
        names.elementAtOrNull(index)?.let {
            values[index] = value
        }
    }

    fun toMap(): Map<String, Boolean> {
        return names.zip(values).toMap()
    }

    override fun iterator(): Iterator<Pair<String, Boolean>> {
        return names.zip(values).iterator()
    }

    val size get() = names.size
}
