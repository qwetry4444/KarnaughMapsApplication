package com.example.karnaughmapsapplication.features.KarnaughMap.presentation

import com.example.karnaughmapsapplication.core.domain.model.KarnaughMap
import com.example.karnaughmapsapplication.core.domain.model.LogicalFunction

data class KarnaughMapUiState(
    val logicalFunction: LogicalFunction,
    val karnaughMap: KarnaughMap = KarnaughMap(logicalFunction)
)
