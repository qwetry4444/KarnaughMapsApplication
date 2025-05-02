package com.example.karnaughmapsapplication.core.domain.logic


import com.example.karnaughmapsapplication.core.domain.model.TokenType

class Parser(private val tokens: List<Token>) {
    private var position = 0

    private fun currentToken(): Token? = tokens.getOrNull(position)

    private fun consume(): Token? = tokens.getOrNull(position++)

    fun parseExpression(): Expression {
        var expr = parseAndExpression()
        while (currentToken()?.type in listOf(TokenType.OR, TokenType.XOR, TokenType.IMPLIES, TokenType.EQU)) {
            val operator = consume()
            val right = parseAndExpression()

            expr = when (operator?.type) {
                TokenType.OR -> Expression.Or(expr, right)
                TokenType.XOR -> Expression.Xor(expr, right)
                TokenType.IMPLIES -> Expression.Implies(expr, right)
                TokenType.EQU -> Expression.Equality(expr, right)
                else -> expr
            }
        }

        return expr
    }

    private fun parseAndExpression(): Expression {
        var expr = parseNotExpression()

        while (currentToken()?.type == TokenType.AND) {
            consume()
            val right = parseNotExpression()
            expr = Expression.And(expr, right)
        }

        return expr
    }

    private fun parseNotExpression(): Expression {
        return if (currentToken()?.type == TokenType.NOT) {
            consume()
            Expression.Not(parseNotExpression())
        } else {
            parsePrimary()
        }
    }

    private fun parsePrimary(): Expression {
        val token = currentToken() ?: throw IllegalArgumentException("Unexpected end of input")

        return when (token.type) {
            TokenType.ONE -> {
                consume()
                Expression.Const(true)
            }
            TokenType.ZERO -> {
                consume()
                Expression.Const(false)
            }
            TokenType.VAR -> {
                consume()
                Expression.Variable(token.value)
            }
            TokenType.OPEN -> {
                consume()
                val expr = parseExpression()
                if (currentToken()?.type != TokenType.CLOSE) {
                    throw IllegalArgumentException("Expected closing parenthesis")
                }
                consume()
                expr
            }
            else -> throw IllegalArgumentException("Unexpected token: ${token.value}")
        }
    }
}
