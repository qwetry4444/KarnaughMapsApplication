package com.example.karnaughmapsapplication.features.KarnaughMap.presentation

import com.example.karnaughmapsapplication.core.domain.parsing.Parser
import com.example.karnaughmapsapplication.core.domain.parsing.tokenize
import com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic.KarnaughMap
import com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic.KarnaughMapMinimizer
import com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic.KarnaughTableFormatter
import com.example.karnaughmapsapplication.core.domain.parsing.LogicalFunction

data class KarnaughMapUiState(
    val logicalFunction: LogicalFunction,
    val karnaughMap: KarnaughMap = KarnaughMap(
        Parser(tokenize(logicalFunction.expression)).parseExpression(),
        logicalFunction.variables
    ),
    val karnaughMapTable: KarnaughTableFormatter = KarnaughTableFormatter(karnaughMap),
    val karnaughMapMinimizer: KarnaughMapMinimizer = KarnaughMapMinimizer(karnaughMap),
    val minimizedLogicalFunction: String = karnaughMapMinimizer.minimize()
)
