package com.example.karnaughmapsapplication.core.domain.di.components

import com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic.GrayCodeGenerator
import com.example.karnaughmapsapplication.core.domain.di.modules.kMapLogicModule
import dagger.Component

@Component(modules = [kMapLogicModule::class])
interface KarnaughMapComponent {
    fun getGrayCodeGenerator() : GrayCodeGenerator
    //fun
}