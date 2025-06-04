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
    val borderSides = remember(row, col, groups) {
        calculateBorders(row, col, groups)
    }

    Box(
        modifier = Modifier
            .size(cellSize)
            .then(borderSides),
        contentAlignment = Alignment.Center
    ) {
        Text(cellText)
    }
}

fun calculateBorders(
    row: Int,
    col: Int,
    groups: List<KarnaughMapMinimizer.Group>
): Modifier {
    val thisCellGroups = groups.filter { it.positions.contains(row to col) }

    fun hasSameGroupAt(r: Int, c: Int): Boolean {
        return thisCellGroups.any { it.positions.contains(r to c) }
    }

    return Modifier
        .then(if (!hasSameGroupAt(row, col - 1)) Modifier.border(BorderStroke(5.dp, Color.Red), RectangleEdge.Left) else Modifier)
        .then(if (!hasSameGroupAt(row, col + 1)) Modifier.border(BorderStroke(5.dp, Color.Red), RectangleEdge.Right) else Modifier)
        .then(if (!hasSameGroupAt(row - 1, col)) Modifier.border(BorderStroke(5.dp, Color.Red), RectangleEdge.Top) else Modifier)
        .then(if (!hasSameGroupAt(row + 1, col)) Modifier.border(BorderStroke(5.dp, Color.Red), RectangleEdge.Bottom) else Modifier)
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
