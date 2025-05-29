package com.example.karnaughmapsapplication.core.domain.parsing

import com.example.karnaughmapsapplication.core.domain.model.OrderedVariables

class LogicalFunction(
    var expression: String = "",
    var variablesCount: Int = 0,
    val variables: OrderedVariables = OrderedVariables(variablesCount)
) {

    fun isValid(): Boolean {

        val binaryOperators = listOf("∧", "V", "⊕", "->", "≡")
        val unaryOperators = listOf("¬")
        val constants = listOf("1", "0")

        var state = States.START
        var openBrackets = 0
        var i = 0

        while (i < expression.length) {
            when (state) {
                States.START, States.OPEN_BRACKET, States.UNARY_OPERATOR -> {
                    when {
                        isVariable(i) -> {
                            state = States.OPERAND
                            i += getVariableLength(i) - 1
                        }

                        expression[i].toString() in constants -> state = States.OPERAND

                        expression[i].toString() in unaryOperators -> state = States.UNARY_OPERATOR

                        expression[i] == '(' -> {
                            openBrackets++
                            state = States.OPEN_BRACKET
                        }
                        else -> return false
                    }
                }
                States.OPERAND, States.CLOSE_BRACKET -> {
                    when {
                        i < expression.length - 1 && expression.substring(i, i + 2) == "->" -> {
                            i++
                            state = States.BINARY_OPERATOR
                        }

                        expression[i].toString() in binaryOperators -> state =
                            States.BINARY_OPERATOR

                        expression[i] == ')' -> {
                            if (openBrackets == 0) return false
                            openBrackets--
                            state = States.CLOSE_BRACKET
                        }
                        else -> return false
                    }
                }
                States.BINARY_OPERATOR -> {
                    when {
                        isVariable(i) -> {
                            state = States.OPERAND
                            i += getVariableLength(i) - 1
                        }

                        expression[i].toString() in constants -> state = States.OPERAND

                        expression[i].toString() in unaryOperators -> state = States.UNARY_OPERATOR

                        expression[i] == '(' -> {
                            openBrackets++
                            state = States.OPEN_BRACKET
                        }
                        else -> return false
                    }
                }
                States.END -> return (state == States.OPERAND || state == States.CLOSE_BRACKET) && openBrackets == 0
            }
            i++
        }

        return (state == States.OPERAND || state == States.CLOSE_BRACKET) && openBrackets == 0
    }

    private fun isVariable(index: Int): Boolean {
        if (expression[index] != 'x') return false
        if (index + 1 >= expression.length) return false
        val num = expression[index + 1].digitToIntOrNull() ?: return false
        return num in 1..variablesCount
    }

    private fun getVariableLength(index: Int): Int {
        return if (isVariable(index)) 2 else 1
    }
}

enum class States { START, OPERAND, BINARY_OPERATOR, UNARY_OPERATOR, OPEN_BRACKET, CLOSE_BRACKET, END }