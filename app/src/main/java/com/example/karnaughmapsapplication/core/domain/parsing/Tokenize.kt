package com.example.karnaughmapsapplication.core.domain.parsing


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
                stringIndex++
            }
            '-' -> {
                tokens.add(Token(TokenType.fromChar(currentChar), currentChar.toString()))
                stringIndex += 2
            }
            'X', 'x' -> {
                val varStart = stringIndex
                while (stringIndex < functionString.length && functionString[stringIndex].isLetterOrDigit() && functionString[stringIndex] != 'V')
                    stringIndex++
                tokens.add(Token(TokenType.VAR, functionString.substring(varStart, stringIndex)))
            }
        }
    }
    return tokens
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