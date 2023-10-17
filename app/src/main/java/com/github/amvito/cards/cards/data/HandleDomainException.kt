package com.github.amvito.cards.cards.data

import com.github.amvito.cards.core.CardException
import com.github.amvito.cards.core.HandleException
import com.github.amvito.cards.core.NoInternetConnection
import com.github.amvito.cards.core.SomethingWentWrong
import java.net.UnknownHostException

class HandleDomainException : HandleException<CardException> {
    override fun handle(e: Exception): CardException {
        return when (e) {
            is UnknownHostException -> NoInternetConnection()
            else -> SomethingWentWrong()
        }
    }
}