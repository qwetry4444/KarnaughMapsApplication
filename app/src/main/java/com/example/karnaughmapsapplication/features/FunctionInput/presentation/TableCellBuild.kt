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
    val groupIndex = groups.indexOfFirst { it.positions.contains(row to col) }
    if (groupIndex == -1) return Modifier // Ячейка не принадлежит ни одной группе

    val color = groupColors[groupIndex % groupColors.size]
    val group = groups[groupIndex]

    fun hasSameGroupAt(r: Int, c: Int): Boolean {
        return group.positions.contains(r to c)
    }

    val drawTop = !hasSameGroupAt(row - 1, col)
    val drawBottom = !hasSameGroupAt(row + 1, col)
    val drawLeft = !hasSameGroupAt(row, col - 1)
    val drawRight = !hasSameGroupAt(row, col + 1)

    return Modifier.drawBehind {
        val strokeWidth = 4.dp.toPx()

        if (drawTop) {
            drawLine(color, Offset(0f, 0f), Offset(size.width, 0f), strokeWidth)
        }
        if (drawBottom) {
            drawLine(color, Offset(0f, size.height), Offset(size.width, size.height), strokeWidth)
        }
        if (drawLeft) {
            drawLine(color, Offset(0f, 0f), Offset(0f, size.height), strokeWidth)
        }
        if (drawRight) {
            drawLine(color, Offset(size.width, 0f), Offset(size.width, size.height), strokeWidth)
        }
    }
}

enum class RectangleEdge {
    Top, Bottom, Left, Right
}

fun Modifier.border(border: BorderStroke, edge: RectangleEdge): Modifier {
    return this.then(
        when (edge) {
            RectangleEdge.Top -> Modifier.drawBehind {
                drawLine(border.brush, Offset(0f, 0f), Offset(size.width, 0f), border.width.value)
            }
            RectangleEdge.Bottom -> Modifier.drawBehind {
                drawLine(border.brush, Offset(0f, size.height), Offset(size.width, size.height), border.width.value)
            }
            RectangleEdge.Left -> Modifier.drawBehind {
                drawLine(border.brush, Offset(0f, 0f), Offset(0f, size.height), border.width.value)
            }
            RectangleEdge.Right -> Modifier.drawBehind {
                drawLine(border.brush, Offset(size.width, 0f), Offset(size.width, size.height), border.width.value)
            }
        }
    )
}


