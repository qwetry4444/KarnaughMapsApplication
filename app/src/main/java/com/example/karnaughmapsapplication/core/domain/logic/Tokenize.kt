package com.example.karnaughmapsapplication.core.domain.logic

import com.example.karnaughmapsapplication.core.domain.model.TokenType

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