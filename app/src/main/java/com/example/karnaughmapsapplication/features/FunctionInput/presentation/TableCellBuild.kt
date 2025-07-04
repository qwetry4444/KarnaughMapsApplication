package com.example.karnaughmapsapplication.features.FunctionInput.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic.KarnaughMapMinimizer

@Composable
fun KarnaughCell(
    cellText: String,
    row: Int,
    col: Int,
    groups: List<KarnaughMapMinimizer.Group>,
    cellSize: Dp
) {
    val groupColors = listOf(
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Magenta,
        Color.Cyan,
        Color.Yellow,
        Color(0xFF8BC34A),  // Lime
        Color(0xFFFF9800),  // Orange
        Color(0xFF9C27B0)   // Purple
    )

    val borderModifier = remember(row, col, groups) {
        calculateBorders(row, col, groups, groupColors)
    }

    Box(
        modifier = Modifier
            .size(cellSize)
            .then(borderModifier),
        contentAlignment = Alignment.Center
    ) {
        Text(cellText)
    }
}

fun calculateBorders(
    row: Int,
    col: Int,
    groups: List<KarnaughMapMinimizer.Group>,
    groupColors: List<Color>
): Modifier {
    // Собираем список индексов тех групп, в которые входит (row, col)
    val matchingGroups = groups.mapIndexedNotNull { index, group ->
        if (group.positions.contains(row to col)) index else null
    }
    if (matchingGroups.isEmpty()) {
        // Если ячейка ни в одну группу не входит
        return Modifier
    }

    // Функция-помощник: проверяет, есть ли в той же группе ячейка-сосед
    fun hasSameGroupAt(group: KarnaughMapMinimizer.Group, r: Int, c: Int): Boolean {
        return group.positions.contains(r to c)
    }

    return Modifier.drawBehind {
        val strokeWidth = 4.dp.toPx()

        // Для каждой группы, в которой содержится (row, col), рисуем свои границы
        matchingGroups.forEachIndexed { idxInList, groupIndex ->
            val group = groups[groupIndex]
            val color = groupColors[groupIndex % groupColors.size]

            // Проверка, надо ли рисовать ту или иную грань
            // (мы считаем, что выходит за пределы, если соседа нет в той же группе)
            val drawTop = !hasSameGroupAt(group, row - 1, col)
            val drawBottom = !hasSameGroupAt(group, row + 1, col)
            val drawLeft = !hasSameGroupAt(group, row, col - 1)
            val drawRight = !hasSameGroupAt(group, row, col + 1)

            // Опционально: смещаем каждую следующую обводку внутрь/наружу,
            // чтобы они не накладывались абсолютно друг на друга.
            // Здесь просто рисуем в одну и ту же позицию (будут перекрывать друг друга),
            // но можно чуть "отступить" смещение для idxInList:
            val offsetMultiplier = idxInList.toFloat()
            val inset = offsetMultiplier * (strokeWidth / 2)

            if (drawTop) {
                drawLine(
                    color = color,
                    start = Offset(x = inset, y = inset),
                    end   = Offset(x = size.width - inset, y = inset),
                    strokeWidth = strokeWidth
                )
            }
            if (drawBottom) {
                drawLine(
                    color = color,
                    start = Offset(x = inset, y = size.height - inset),
                    end   = Offset(x = size.width - inset, y = size.height - inset),
                    strokeWidth = strokeWidth
                )
            }
            if (drawLeft) {
                drawLine(
                    color = color,
                    start = Offset(x = inset, y = inset),
                    end   = Offset(x = inset, y = size.height - inset),
                    strokeWidth = strokeWidth
                )
            }
            if (drawRight) {
                drawLine(
                    color = color,
                    start = Offset(x = size.width - inset, y = inset),
                    end   = Offset(x = size.width - inset, y = size.height - inset),
                    strokeWidth = strokeWidth
                )
            }
        }
    }
}



