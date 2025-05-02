package com.example.karnaughmapsapplication.features.KarnaughMap.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.karnaughmapsapplication.core.domain.model.LogicalFunction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class KarnaughMapViewModel(logicalFunction: LogicalFunction) : ViewModel()  {
    private var _uiState = MutableStateFlow(KarnaughMapUiState(logicalFunction))
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value.karnaughMap.tableFill()
    }

}

class KarnaughMapViewModelFactory(private val logicalFunction: LogicalFunction) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(KarnaughMapViewModel::class.java)) {
            return KarnaughMapViewModel(logicalFunction) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}