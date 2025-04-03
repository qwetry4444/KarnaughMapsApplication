package com.example.karnaughmapsapplication.features.KarnaughMap.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class KarnaughMapViewModel: ViewModel() {
    private var _uiState = MutableStateFlow(KarnaughMapUiState())
    val uiState = _uiState.asStateFlow()

}