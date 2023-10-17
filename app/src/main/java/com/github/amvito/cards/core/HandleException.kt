package com.github.amvito.cards.core

import com.github.amvito.cards.R
import com.github.amvito.cards.cards.data.cloud.ResourceNotFound
import kotlin.Exception

interface HandleException<T> {

    fun handle(e: Exception): T

    class Base(
        private val manageResources: ManageResources
    ) : HandleException<String> {
        override fun handle(e: Exception): String {
            return manageResources.string(
                when (e) {
                    is NoInternetConnection -> R.string.no_internet_connection
                    is ResourceNotFound -> R.string.resource_not_found
                    else -> R.string.something_went_wrong
                }
            )
        }
    }
}


abstract class CardException : Exception()

class SomethingWentWrong : CardException()

class NoInternetConnection : CardException()