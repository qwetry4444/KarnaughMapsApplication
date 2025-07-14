package com.example.karnaughmapsapplication.core.domain.di

import com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic.GrayCodeGenerator
import dagger.Component

@Component(modules = [kMapLogicModule::class])
interface KarnaughMapComponent {
    fun getGrayCodeGenerator() : GrayCodeGenerator
    //fun
}