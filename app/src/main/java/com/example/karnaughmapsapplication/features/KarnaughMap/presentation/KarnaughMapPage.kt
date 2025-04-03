package com.example.karnaughmapsapplication.features.KarnaughMap.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.karnaughmapsapplication.core.domain.model.LogicalFunction

@Composable
fun KarnaughMapPage(logicalFunction: LogicalFunction, navController: NavHostController) {
    Column {
        MapTopAppBar(navController)
        Spacer(modifier = Modifier.height(32.dp))

        Column(modifier = Modifier.padding(32.dp)) {
            OriginalFunction(logicalFunction.expression)
            Spacer(modifier = Modifier.height(32.dp))

            KarnaughMap()
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
fun KarnaughMap(){
    Text("Карта Карно\n" +
            "...")
}

@Composable
fun FunctionMinimization(){
    Text("Минимизация фукнции\n" +
            "...")
}