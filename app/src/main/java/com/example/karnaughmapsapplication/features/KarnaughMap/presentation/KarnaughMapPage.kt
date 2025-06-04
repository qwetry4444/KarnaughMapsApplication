package com.example.karnaughmapsapplication.features.KarnaughMap.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic.KarnaughMapMinimizer
import com.example.karnaughmapsapplication.core.domain.parsing.LogicalFunction
import com.example.karnaughmapsapplication.features.FunctionInput.presentation.KarnaughCell

@Composable
fun KarnaughMapPage(logicalFunction: LogicalFunction, navController: NavHostController) {
    val karnaughMapViewModelFactory = KarnaughMapViewModelFactory(logicalFunction)
    val viewModel: KarnaughMapViewModel = viewModel(factory = karnaughMapViewModelFactory)
    val uiState: KarnaughMapUiState by viewModel.uiState.collectAsState()

    Column {
        MapTopAppBar(navController)
        Spacer(modifier = Modifier.height(32.dp))

        Column(modifier = Modifier.padding(32.dp)) {
            OriginalFunction(uiState.logicalFunction.expression)
            Spacer(modifier = Modifier.height(32.dp))

            KarnaughMapTable(
                uiState.karnaughMapTable.table,
                uiState.karnaughMapTable.colsTitle,
                uiState.karnaughMapTable.rowsTitle,
                uiState.karnaughMapMinimizer.groups
            )

            Spacer(modifier = Modifier.height(32.dp))

            FunctionMinimization(uiState.minimizedLogicalFunction)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapTopAppBar(navController: NavHostController){
    TopAppBar(
        title = {
            Text("Карта Карно")
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun OriginalFunction(logicalExpression: String){
    Column {
        Text("Исходная функция")
        Text(logicalExpression)
    }
}

@Composable
fun KarnaughMapTable(
    data: List<List<String>>,
    colTitle: String,
    rowTitle: String,
    groups: List<KarnaughMapMinimizer.Group> = listOf()
) {
    val cellSize = 40.dp
    val rowCount = data.size
    val colCount = data.firstOrNull()?.size ?: 0

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HeaderSection(cellSize, colCount, colTitle, data.first())
        RowHeadersAndData(cellSize, rowCount, colCount, data, rowTitle, groups)
    }
}

@Composable
private fun HeaderSection(cellSize: Dp, colCount: Int, colTitle: String, columnHeaders: List<String>) {
    Row {
        FBox(cellSize)
        Column(modifier = Modifier.border(2.dp, Color.Black).background(Color.LightGray)) {
            ColumnTitleBox(cellSize, colCount, colTitle)
            ColumnHeadersRow(cellSize, colCount, columnHeaders)
        }
    }
}

@Composable
private fun FBox(cellSize: Dp) {
    Box(
        modifier = Modifier
            .size(cellSize * 2)
            .border(2.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text("F", fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ColumnTitleBox(cellSize: Dp, colCount: Int, colTitle: String) {
    Box(
        modifier = Modifier
            .width(cellSize * (colCount - 1))
            .height(cellSize)
            .border(2.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(colTitle, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ColumnHeadersRow(cellSize: Dp, colCount: Int, columnHeaders: List<String>) {
    Row {
        for (col in 1 until colCount) {
            Box(
                modifier = Modifier
                    .size(cellSize)
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(columnHeaders[col], fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun RowHeadersAndData(
    cellSize: Dp,
    rowCount: Int,
    colCount: Int,
    data: List<List<String>>,
    rowTitle: String,
    groups: List<KarnaughMapMinimizer.Group>
) {
    Row {
        RowHeaders(cellSize, rowCount, data, rowTitle)
        Column {
            for (i in 1 until rowCount) {
                Row {
                    for (j in 1 until colCount) {
                        KarnaughCell(data[i][j], i, j, groups, cellSize)
                    }
                }
            }
        }
    }
}

@Composable
private fun RowHeaders(
    cellSize: Dp,
    rowCount: Int,
    data: List<List<String>>,
    rowTitle: String
) {
    Row(modifier = Modifier.border(2.dp, Color.Black).background(Color.LightGray)) {
        Box(
            modifier = Modifier
                .width(cellSize)
                .height(cellSize * (rowCount - 1))
                .border(1.dp, Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(rowTitle, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }

        Column {
            repeat(rowCount - 1) {
                Box(
                    modifier = Modifier
                        .size(cellSize)
                        .border(1.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(data[it + 1][0], fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}







@Composable
fun FunctionMinimization(minimizedLogicalFunction: String){
    Text(
        "Минимизированная фукнция\n$minimizedLogicalFunction"
            )
}

@Preview(showBackground = true)
@Composable
fun TablePreview(){
    //KarnaughMapTable(2,2, listOf(listOf(true, false), listOf(false, true)))
}