package com.example.karnaughmapsapplication.core.domain.model

import com.example.karnaughmapsapplication.core.domain.logic.Expression
import com.example.karnaughmapsapplication.core.domain.logic.Parser

class KarnaughMap(
    val logicalFunction: LogicalFunction,
    val variables: MutableList<Boolean> = MutableList(logicalFunction.variablesCount) { false },
    val table: MutableList<MutableList<Boolean>> = MutableList(logicalFunction.variablesCount / 2 + logicalFunction.variablesCount % 2) {
        MutableList(logicalFunction.variablesCount / 2) { false }
    },
    val expression: Expression = Parser(tokenize(logicalFunction.expression)).parseExpression()
) {

    fun incrementVariables(): Boolean{
        for (i in variables.size downTo 0){
            if(!variables[i]){
                variables[i] = true
                return true
            }
            variables[i] = false
        }
        return false
    }


    fun tableFill(){
        for (i in 0..table.size){
            for (j in 0..table[i].size){
                table[i][j] = expression.evaluate(variables[i])
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

data class Token(
    val type: TokenType,
    val value: String
)


fun tokenize(functionString: String) : MutableList<Token> {
    val tokens: MutableList<Token> = mutableListOf()

    var stringIndex = 0
    var currentChar: Char

    while (stringIndex < functionString.length) {
        currentChar = functionString[stringIndex]

        when (currentChar) {
            '¬', '∧', 'V', '⊕', '≡', '(', ')', '0', '1' -> {
                tokens.add(Token(TokenType.fromChar(currentChar), currentChar.toString()))
            }
            '-' -> {
                tokens.add(Token(TokenType.fromChar(currentChar), currentChar.toString()))
                stringIndex++
            }
            'X' -> {
                val varStart = stringIndex
                while (stringIndex < functionString.length && functionString[stringIndex].isLetterOrDigit())
                    stringIndex++
                tokens.add(Token(TokenType.VAR, functionString.substring(varStart, stringIndex)))
                stringIndex++
            }
        }
        stringIndex++
    }
    return tokens
}



//sealed class Expression {
//    data class Variable(val name: String): Expression()
//    data class ConstOne(val value: Boolean): Expression()
//    data class ConstZero(val value: Boolean): Expression()
//
//    data class And(val leftExpression: Expression, val rightExpression: Expression): Expression()
//    data class Or(val leftExpression: Expression, val rightExpression: Expression): Expression()
//    data class Xor(val leftExpression: Expression, val rightExpression: Expression): Expression()
//    data class Equality(val leftExpression: Expression, val rightExpression: Expression): Expression()
//    data class Implies(val leftExpression: Expression, val rightExpression: Expression): Expression()
//    data class Not(val expression: Expression): Expression()
//}

