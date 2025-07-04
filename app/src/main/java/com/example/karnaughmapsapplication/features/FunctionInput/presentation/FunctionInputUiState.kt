package com.example.karnaughmapsapplication.features.FunctionInput.presentation

import androidx.compose.ui.text.input.TextFieldValue

data class FunctionInputUiState(
    val currentVariableCountString: String = "",
    val currentVariableCount: Int = 3,
    val currentFunctionInput: TextFieldValue = TextFieldValue(""),
    val error: String? = null,
    val errorType: ErrorType? = null
)

enum class ErrorType {
    WrongFunctionInput
}