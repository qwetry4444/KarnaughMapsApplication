package com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic

import com.example.karnaughmapsapplication.core.domain.parsing.Expression
import kotlin.math.pow

class KarnaughMap(
    private val expression: Expression,
    val variables: OrderedVariables
) {
    private val grayCodeGenerator: GrayCodeGenerator = GrayCodeGenerator()

    var rowVariablesCount = variables.size / 2
    var colVariablesCount = variables.size / 2 + variables.size % 2

    val rowCount = 2.0.pow(rowVariablesCount).toInt()
    val colCount = 2.0.pow(colVariablesCount).toInt()

    var colsHeaders = MutableList(colCount) {""}
    var rowsHeaders = MutableList(rowCount) {""}
    val tableValues: MutableList<MutableList<Boolean>> = MutableList(rowCount) {
        MutableList(colCount) { false }
    }

    init {
        tableFill()
    }

    fun tableFill(){
        for (i in 0 until rowCount ) {
            // ??????????????????
            rowsHeaders[i] = grayCodeGenerator.getCodeString(variables.size).substring(0, rowVariablesCount)
            for (j in 0 until colCount ){
                if (i == 0){
                    colsHeaders[j] = grayCodeGenerator.getCodeString(variables.size).substring(rowVariablesCount)
                }
                tableValues[i][j] = expression.evaluate(variables.toMap())
                grayCodeGenerator.getNextVars(variables)
            }
        }
    }
}









class KarnaughTableFormatter(
    val kMap: KarnaughMap
) {
    val table: MutableList<MutableList<String>> =
        MutableList(kMap.rowCount + 1) { MutableList(kMap.colCount + 1) {""} }
    val rowsTitle =
        (0 until  kMap.rowVariablesCount).map {
            kMap.variables.getByIndex(it).first
        }.joinToString("")
    val colsTitle =
        (kMap.rowVariablesCount until kMap.rowVariablesCount + kMap.colVariablesCount).map {
            kMap.variables.getByIndex(it).first
        }.joinToString("")

    fun fillTable() {
        for (i in 0..kMap.rowCount){
            for (j in 0..kMap.colCount) {
                if (i == 0) {
                    if (j == 0)
                        continue
                    table[i][j] = kMap.colsHeaders[j - 1]
                } else if (j == 0) {
                    table[i][j] = kMap.rowsHeaders[i - 1]
                } else {
                    table[i][j] = if (kMap.tableValues[i - 1][j - 1]) "1" else "0"
                }
            }
        }
    }

    init {
        fillTable()
    }

}







