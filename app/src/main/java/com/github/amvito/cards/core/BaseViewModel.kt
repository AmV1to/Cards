package com.github.amvito.cards.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

abstract class BaseViewModel(
    private val runAsync: RunAsync,
) : ViewModel(), Handle {

    override fun <T : Any> handle(block: suspend () -> T, ui: (T) -> Unit) {
        runAsync.runAsync(viewModelScope, block, ui)
    }
}

interface Handle {
    fun <T : Any> handle(
        block: suspend () -> T, ui: (T) -> Unit
    )
}