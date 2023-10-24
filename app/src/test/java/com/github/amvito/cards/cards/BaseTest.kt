package com.github.amvito.cards.cards

import com.github.amvito.cards.core.RunAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

abstract class BaseTest {

    protected class FakeRunAsync : RunAsync {
        override fun <T : Any> runAsync(
            scope: CoroutineScope,
            block: suspend () -> T,
            ui: (T) -> Unit
        ) = runBlocking {
            block.invoke().let { ui.invoke(it) }
        }
    }
}