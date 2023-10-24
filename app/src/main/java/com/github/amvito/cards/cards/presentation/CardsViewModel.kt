package com.github.amvito.cards.cards.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.amvito.cards.core.BaseViewModel
import com.github.amvito.cards.core.RunAsync

class CardsViewModel(
    runAsync: RunAsync,
    private val handleFetchCards: HandleFetchCards,
    private val cardsCommunication: CardsCommunication,
) : BaseViewModel(runAsync), ObserveCards, CardDetails {

    fun fetchCards(cardsCount: Int) = handleFetchCards.fetch(this, cardsCount)

    fun tryAgain(cardsCount: Int) = handleFetchCards.fetch(this, cardsCount)

    override fun showDetails(card: CardUi) = cardsCommunication.showDetails(card)
    override fun observeState(owner: LifecycleOwner, observe: Observer<CardUiState>) =
        cardsCommunication.observeState(owner, observe)

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        cardsCommunication.observeProgress(owner, observer)
}

interface CardDetails {
    fun showDetails(card: CardUi)
}
