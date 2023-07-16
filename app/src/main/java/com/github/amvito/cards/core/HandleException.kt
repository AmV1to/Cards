package com.github.amvito.cards.core

import com.github.amvito.cards.R
import kotlin.Exception

interface HandleException<T> {

    fun handle(e: Exception): T

    class Base(
        private val manageResources: ManageResources
    ) : HandleException<String> {
        override fun handle(e: Exception): String {
            val id = R.string.something_went_wrong
            return manageResources.string(id)
        }
    }

    class ThrowException : HandleException<Exception> {
        override fun handle(e: Exception): Exception {
            return when(e) {
                else -> SomethingWentWrong()
            }
        }
    }

}

abstract class CardException : Exception()

class SomethingWentWrong : CardException()