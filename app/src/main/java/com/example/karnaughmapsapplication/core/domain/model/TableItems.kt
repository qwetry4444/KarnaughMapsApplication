package com.example.karnaughmapsapplication.core.domain.model

sealed class TableItem {
    class Constant(
        val value: Boolean
    ) : TableItem() {
        companion object{
            fun getAllConstants(): List<Constant> { return listOf(Constant(false), Constant(true))}
        }
    }

    class Variable(
        val name: String,
        val value: Boolean
    ) : TableItem() {
        companion object{
            fun getAllVariables(variablesCount: Int): List<Variable> {
                return (1..variablesCount).map { Variable("x$it", false) }
            }
        }
    }

    class Operation(
        val operation: OperationType
    ) : TableItem() {
        companion object{
            fun getAllOperations(): List<Operation> {
                return OperationType.entries.map { Operation(it) }
            }
        }
    }

    class Bracket(
        val type : BracketType
    ) : TableItem() {
        companion object{
            fun getAllBrackets(): List<Bracket> {
                return listOf(Bracket(BracketType.OpenBracket), Bracket(BracketType.CloseBracket))
            }
        }

        enum class BracketType {
            OpenBracket, CloseBracket
        }
    }

    fun tableItemToString(): String {
        return when(this){
            is Constant -> if (this.value) "1" else "0"
            is Variable -> this.name
            is Operation -> this.operation.operationToString()
            is Bracket -> if (this.type == Bracket.BracketType.OpenBracket) "(" else ")"
        }
    }


    companion object {
        fun getAllTableItems(variablesCount: Int) : List<TableItem> {
            return Constant.getAllConstants() +
                    Variable.getAllVariables(variablesCount) +
                    Operation.getAllOperations() +
                    Bracket.getAllBrackets()
        }
    }

}

enum class OperationType{
    Or, And, Xor, Implication, Equivalence, Not;

    fun operationToString(): String {
        return when(this){
            Or -> "V"
            And -> "∧"
            Xor -> "⊕"
            Implication -> "->"
            Equivalence -> "≡"
            Not -> "¬"
        }
    }
}


fun getTableItemsList(variablesCount: Int): List<TableItem> {
    return TableItem.getAllTableItems(variablesCount)
}