package com.example.karnaughmapsapplication.features.KarnaughMap.presentation

import com.example.karnaughmapsapplication.core.domain.parsing.Parser
import com.example.karnaughmapsapplication.core.domain.parsing.tokenize
import com.example.karnaughmapsapplication.core.domain.model.KarnaughMap
import com.example.karnaughmapsapplication.core.domain.model.KarnaughTableFormatter
import com.example.karnaughmapsapplication.core.domain.parsing.LogicalFunction

data class KarnaughMapUiState(
    val logicalFunction: LogicalFunction,
    val karnaughMapTable: KarnaughTableFormatter = KarnaughTableFormatter(
        KarnaughMap(
        Parser(tokenize(logicalFunction.expression)).parseExpression(),
        logicalFunction.variables
        )
    )
)
