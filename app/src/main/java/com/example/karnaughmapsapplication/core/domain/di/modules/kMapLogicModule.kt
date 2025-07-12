package com.example.karnaughmapsapplication.core.domain.di.modules

import com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic.GrayCodeGenerator
import com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic.KarnaughMap
import com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic.OrderedVariables
import com.example.karnaughmapsapplication.core.domain.parsing.Expression
import com.example.karnaughmapsapplication.core.domain.parsing.LogicalFunction
import dagger.Module
import dagger.Provides

@Module
class kMapLogicModule {
    @Provides
    fun provideOrderedVariables(
        varsCount: Int,
        varsNames: List<String>?)
    : OrderedVariables {
        return OrderedVariables(
            varsCount,
            varsNames
        )
    }

    @Provides
    fun provideLogicalFunction(
        expression: String,
        varsCount: Int,
        varsNames: List<String>?)
    : LogicalFunction {
        return LogicalFunction(
            expression,
            varsCount,
            provideOrderedVariables(
                varsCount,
                varsNames
            )
        )
    }

    @Provides
    fun provideGrayCodeGenerator() : GrayCodeGenerator {
        return GrayCodeGenerator()
    }

    @Provides
    fun provideKarnaughMap(
        expression: Expression,
        orderedVariables: OrderedVariables,
        grayCodeGenerator: GrayCodeGenerator)
    : KarnaughMap{
        return KarnaughMap(
            expression,
            orderedVariables,
            grayCodeGenerator
        )
    }


}