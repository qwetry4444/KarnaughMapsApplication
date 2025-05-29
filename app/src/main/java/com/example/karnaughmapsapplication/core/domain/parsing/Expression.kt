package com.example.karnaughmapsapplication.core.domain.parsing

sealed class Expression {
    data class Variable(val name: String) : Expression() {
        override fun evaluate(variables: Map<String, Boolean>): Boolean {
            return variables[name] ?: false
        }
    }

    data class Const(val value: Boolean) : Expression() {
        override fun evaluate(variables: Map<String, Boolean>): Boolean {
            return value
        }
    }

    data class And(val left: Expression, val right: Expression) : Expression() {
        override fun evaluate(variables: Map<String, Boolean>): Boolean {
            return left.evaluate(variables) && right.evaluate(variables)
        }
    }

    data class Or(val left: Expression, val right: Expression) : Expression() {
        override fun evaluate(variables: Map<String, Boolean>): Boolean {
            return left.evaluate(variables) || right.evaluate(variables)
        }
    }

    data class Xor(val left: Expression, val right: Expression) : Expression() {
        override fun evaluate(variables: Map<String, Boolean>): Boolean {
            return left.evaluate(variables) != right.evaluate(variables)
        }
    }

    data class Equality(val left: Expression, val right: Expression) : Expression() {
        override fun evaluate(variables: Map<String, Boolean>): Boolean {
            return left.evaluate(variables) == right.evaluate(variables)
        }
    }

    data class Implies(val left: Expression, val right: Expression) : Expression() {
        override fun evaluate(variables: Map<String, Boolean>): Boolean {
            return !left.evaluate(variables) || right.evaluate(variables)
        }
    }

    data class Not(val expr: Expression) : Expression() {
        override fun evaluate(variables: Map<String, Boolean>): Boolean {
            return !expr.evaluate(variables)
        }
    }

    abstract fun evaluate(variables: Map<String, Boolean>): Boolean
}
