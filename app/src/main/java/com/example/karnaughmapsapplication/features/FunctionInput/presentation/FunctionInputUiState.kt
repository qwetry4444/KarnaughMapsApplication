package com.example.karnaughmapsapplication.features.FunctionInput.presentation

import androidx.compose.ui.text.input.TextFieldValue

data class FunctionInputUiState(
    val currentVariableCountString: String = "3",
    val currentVariableCount: Int = 3,
    val currentFunctionInput: TextFieldValue = TextFieldValue("x1Vx2Vx3"),
    val error: String? = null,
    val errorType: ErrorType? = null
)

enum class ErrorType {
    WrongFunctionInput
}