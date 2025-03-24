package com.example.karnaughmapsapplication.presentation

data class FunctionInputUiState(
    val currentVariableCountString: String = "",
    val currentVariableCount: Int = 1,
    val currentFunctionInput: String = "",
    val error: String? = null
)