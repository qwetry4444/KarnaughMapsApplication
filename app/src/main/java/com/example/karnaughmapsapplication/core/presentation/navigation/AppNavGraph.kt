package com.example.karnaughmapsapplication.core.presentation.navigation

import FunctionInputPage
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.karnaughmapsapplication.core.domain.parsing.LogicalFunction
import com.example.karnaughmapsapplication.features.KarnaughMap.presentation.KarnaughMapPage

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.FunctionInput
) {
    NavHost(
        navController,
        startDestination.route
    ) {
        composable(Screen.FunctionInput.route) {
            FunctionInputPage(navController)
        }

        composable(
            route = "karnaugh_map/{expression}/{variablesCount}",
            arguments = listOf(
                navArgument("expression") { type = NavType.StringType },
                navArgument("variablesCount") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val expression = backStackEntry.arguments?.getString("expression") ?: ""
            val variablesCount = backStackEntry.arguments?.getInt("variablesCount") ?: 0
            val logicalFunction = LogicalFunction(expression, variablesCount)
            KarnaughMapPage(logicalFunction, navController)
        }
    }
}