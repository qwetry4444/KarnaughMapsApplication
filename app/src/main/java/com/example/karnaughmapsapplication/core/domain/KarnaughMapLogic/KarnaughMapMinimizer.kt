package com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic

class KarnaughMapMinimizer(private val map: KarnaughMap) {

    data class Group(val positions: List<Pair<Int, Int>>)

    val groups: List<Group>
    val terms: List<String>


    init {
        groups = findGroups()
        terms = groups.map { group -> groupToTerm(group) }
    }

    fun minimize(): String {
        return terms.joinToString(" V ")
    }

    private fun findGroups(): List<Group> {
        val visited = Array(map.rowCount) { BooleanArray(map.colCount) }
        val result = mutableListOf<Group>()

        for (i in 0 until map.rowCount) {
            for (j in 0 until map.colCount) {
                if (map.tableValues[i][j]) {
                    val groupCells = collectGroup(i, j, visited)
                    if (groupCells.isNotEmpty()) {
                        // Mark all cells in the found group as visited
                        groupCells.forEach { (x, y) -> visited[x][y] = true }
                        result.add(Group(groupCells))
                    }

                }
            }
        }

        return result
    }

    private fun collectGroup(startI: Int, startJ: Int, visited: Array<BooleanArray>): List<Pair<Int, Int>> {
        val maxRows = map.rowCount
        val maxCols = map.colCount
        val rowMaxSize = highestPowerOfTwo(maxRows)
        val colMaxSize = highestPowerOfTwo(maxCols)

        var bestGroup: List<Pair<Int, Int>> = emptyList()

        val rowSizes = generateSizes(rowMaxSize)
        val colSizes = generateSizes(colMaxSize)

        for (height in rowSizes) {
            for (width in colSizes) {
                val groupCells = mutableListOf<Pair<Int, Int>>()
                var allCellsOne = true

                for (di in 0 until height) {
                    for (dj in 0 until width) {
                        val x = (startI + di) % maxRows
                        val y = (startJ + dj) % maxCols

                        if (!map.tableValues[x][y]) {
                            allCellsOne = false
                            break
                        }
                        groupCells.add(x to y)
                    }
                    if (!allCellsOne) break
                }

                // If all cells in rectangle are '1'
                if (allCellsOne) {
                    // Check if not all cells already visited
                    val anyUnvisited = groupCells.any { (x, y) -> !visited[x][y] }
                    if (anyUnvisited && groupCells.size > bestGroup.size) {
                        bestGroup = groupCells.toList()
                    } else break
                }
            }
        }

        return bestGroup
    }

    private fun generateSizes(maxSize: Int): List<Int> {
        val sizes = mutableListOf<Int>()
        var s = maxSize
        while (s >= 1) {
            sizes.add(s)
            s /= 2
        }
        return sizes
    }

    // Compute highest power of two <= n
    private fun highestPowerOfTwo(n: Int): Int {
        var p = 1
        while (p * 2 <= n) p *= 2
        return p
    }

    private fun groupToTerm(group: Group): String {
        val variablesList = mutableListOf<String>()
        val rowCountBits = map.rowVariablesCount
        val colCountBits = map.colVariablesCount

        for (index in 0 until map.variables.size) {
            // Determine if this variable is row or column variable
            val isRowVar = index < rowCountBits
            // Check value for first position
            val (firstI, firstJ) = group.positions.first()
            val referenceBit = if (isRowVar) {
                map.rowsHeaders[firstI][index]
            } else {
                map.colsHeaders[firstJ][index - rowCountBits]
            }

            // Check that for all positions the bit is same
            val allSame = group.positions.all { (i, j) ->
                val bit =
                    if (isRowVar) map.rowsHeaders[i][index] else map.colsHeaders[j][index - rowCountBits]
                bit == referenceBit
            }
            if (allSame) {
                val name = map.variables.getByIndex(index).first
                if (referenceBit == '1') variablesList.add(name) else variablesList.add("¬${name}")
            }
        }
        return variablesList.joinToString(" V ")
    }

    private fun getBitAt(position: Pair<Int, Int>, bitIndex: Int): Boolean {
        val (i, j) = position
        val rowCode = i.toString(2).padStart(map.rowVariablesCount, '0')
        val colCode = j.toString(2).padStart(map.colVariablesCount, '0')
        val full = rowCode + colCode
        return full[bitIndex] == '1'
    }
}
