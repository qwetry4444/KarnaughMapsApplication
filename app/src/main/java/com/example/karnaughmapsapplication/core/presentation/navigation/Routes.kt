package com.example.karnaughmapsapplication.core.presentation.navigation

import com.example.karnaughmapsapplication.core.domain.parsing.LogicalFunction

sealed class Screen(val route: String) {
    data object FunctionInput : Screen("function_input")
    data class KarnaughMap(val logicalFunction: LogicalFunction) : Screen("karnaugh_map/{logicalFunction}") {
        fun createRoute() = "karnaugh_map/${logicalFunction.expression}/${logicalFunction.variablesCount}"
    }
}