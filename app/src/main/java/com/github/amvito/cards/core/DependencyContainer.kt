package com.github.amvito.cards.core

import androidx.lifecycle.ViewModel
import com.github.amvito.cards.cards.CardsModule
import com.github.amvito.cards.cards.presentation.CardsViewModel
import com.github.amvito.cards.details.CardDetailsModule
import com.github.amvito.cards.details.presentation.CardDetailsViewModel
import com.github.amvito.cards.main.MainModule
import com.github.amvito.cards.main.MainViewModel

interface DependencyContainer {

    fun <T : ViewModel> module(clazz: Class<T>): Module<*>


    object Error : DependencyContainer {
        override fun <T : ViewModel> module(clazz: Class<T>): Module<*> {
            throw IllegalStateException("Module not found $clazz")
        }
    }

    class Base(
        private val core: Core,
        private val dependencyContainer: DependencyContainer = Error
    ) : DependencyContainer {
        override fun <T : ViewModel> module(clazz: Class<T>): Module<*> {
            return when (clazz) {
                CardDetailsViewModel::class.java -> CardDetailsModule(core)
                CardsViewModel::class.java -> CardsModule(core)
                MainViewModel::class.java -> MainModule(core)
                else -> dependencyContainer.module(clazz)
            }
        }
    }
}