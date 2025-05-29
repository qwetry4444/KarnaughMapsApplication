package com.example.karnaughmapsapplication.features.KarnaughMap.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.karnaughmapsapplication.core.domain.parsing.LogicalFunction

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
                uiState.karnaughMapTable.rowsTitle
            )

            Spacer(modifier = Modifier.height(32.dp))

            FunctionMinimization()
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
fun KarnaughMapTable(data: List<List<String>>, columnsTitle: String, rowsTitle: String){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        repeat(data.size + 1) { row ->
            Row {
                repeat(data.first().size + 1) { col ->
                    if (row == 0 || col == 0)
                        Box(modifier = Modifier
                            .border(2.dp, Color.Black)
                            .padding(10.dp)
                            .width(25.dp)
                        ){
                            Text(
                                if (row == 0 && col == 0)
                                    "F"
                                else if (col == 0) rowsTitle
                                else columnsTitle
                            )
                        }
                    else
                        Box(modifier = Modifier
                            .border(2.dp, Color.Black)
                            .background(Color.LightGray)
                            .padding(10.dp)
                            .width(25.dp)
                        ){
                            Text(data[row - 1][col - 1])
                        }

                }
            }
        }

    }
}

@Composable
fun FunctionMinimization(){
    Text("Минимизация фукнции\n" +
            "...")
}

@Preview(showBackground = true)
@Composable
fun TablePreview(){
    //KarnaughMapTable(2,2, listOf(listOf(true, false), listOf(false, true)))
}