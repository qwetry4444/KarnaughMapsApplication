package com.example.karnaughmapsapplication.features.KarnaughMap.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.karnaughmapsapplication.core.domain.model.LogicalFunction

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

            KarnaughMapTable(uiState.karnaughMap.table.size, uiState.karnaughMap.table.first().size, uiState.karnaughMap.table)
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
fun KarnaughMapTable(rows: Int, cols: Int, data: List<List<Boolean>>){
    Column(Modifier.background(Color.LightGray), horizontalAlignment = Alignment.CenterHorizontally) {
        repeat(cols) { col ->
            Row {
                repeat(rows) { row ->
                    Box(modifier = Modifier
                        .border(2.dp, Color.Black)
                        .padding(20.dp)
                    ){
                        Text(if (data[row][col]) "1" else "0")
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
    KarnaughMapTable(2,2, listOf(listOf(true, false), listOf(false, true)))
}