package com.github.amvito.cards.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface RunAsync {

    fun <T : Any> runAsync(
        scope: CoroutineScope, block: suspend () -> T, ui: (T) -> Unit
    )

    class Base(
        private val dispatchersList: DispatchersList,
    ) : RunAsync {
        override fun <T : Any> runAsync(
            scope: CoroutineScope,
            block: suspend () -> T,
            ui: (T) -> Unit
        ) {
            scope.launch {
                val result = block.invoke()
                withContext(dispatchersList.ui()) {
                    ui.invoke(result)
                }
            }
        }

    }
}