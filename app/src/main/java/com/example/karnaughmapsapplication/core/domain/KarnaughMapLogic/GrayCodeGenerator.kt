package com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic

class GrayCodeGenerator {
    var grayCodeString: String = "0"
        private set
    private var iterationNumber: Int = 0

    fun next() {
        iterationNumber++
        val grayCodeNumber = iterationNumber.xor(iterationNumber.shr(1))
        grayCodeString = Integer.toBinaryString(grayCodeNumber)
    }

    fun getNextVars(vars: OrderedVariables){
        next()
        val codeList = grayCodeString.padStart(vars.size, '0').toList()

        codeList.forEachIndexed { index, c ->
            vars.setByIndex(index, c.toBoolean())
        }
    }

    fun getCodeString(bitCount: Int) : String {
        return grayCodeString.padStart(bitCount, '0')
    }

    private fun Char.toBoolean() : Boolean = when(this) {
        '1' -> true
        '0' -> false
        else -> throw IllegalArgumentException("Invalid char: $this")
    }
}