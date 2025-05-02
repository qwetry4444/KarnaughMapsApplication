package com.example.karnaughmapsapplication.core.domain.model

import com.example.karnaughmapsapplication.core.domain.logic.Expression
import com.example.karnaughmapsapplication.core.domain.logic.Parser
import com.example.karnaughmapsapplication.core.domain.logic.tokenize

class KarnaughMap(
    val logicalFunction: LogicalFunction,
    val variables: MutableMap<String, Boolean> = mutableMapOf(),
    val table: MutableList<MutableList<Boolean>> = MutableList(
        logicalFunction.variablesCount + logicalFunction.variablesCount % 2
    ) {
        MutableList(logicalFunction.variablesCount - logicalFunction.variablesCount % 2) { false }
    },
    val expression: Expression = Parser(tokenize(logicalFunction.expression)).parseExpression()
) {

    init {
        (1..logicalFunction.variablesCount).forEach { i ->
            variables["x$i"] = false
        }
    }

    fun incrementVariables(): Boolean {
        val keys = variables.keys.sortedBy { it.substring(1).toInt() }.reversed()

        for (key in keys) {
            if (variables[key] == false) {
                variables[key] = true
                return true
            }
            variables[key] = false
        }
        return false
    }


    fun tableFill(){
        for (i in 0..<table.size){
            for (j in 0..<table[i].size){
                table[i][j] = expression.evaluate(variables)
                incrementVariables()
            }
        }
    }
}

enum class TokenType {
    VAR, AND, OR, NOT,
    XOR, IMPLIES, EQU,
    OPEN, CLOSE,
    ONE, ZERO, NONE;

    companion object {
        fun fromChar(char: Char): TokenType {
            return when (char) {
                '∧' -> AND
                'V' -> OR
                '¬' -> NOT
                '⊕' -> XOR
                '(' -> OPEN
                ')' -> CLOSE
                '≡' -> EQU
                '-' -> IMPLIES
                '0' -> ZERO
                '1' -> ONE
                else -> NONE
            }
        }
    }
}




