package com.github.amvito.cards.cards.presentation

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.amvito.cards.cards.domain.CardDomain
import com.github.amvito.cards.cards.domain.CardsInteractor
import com.github.amvito.cards.cards.domain.CardsResult
import com.github.amvito.cards.core.BaseViewModel
import com.github.amvito.cards.core.Communication
import com.github.amvito.cards.core.Navigation
import com.github.amvito.cards.core.NavigationCommunication
import com.github.amvito.cards.core.RunAsync
import com.github.amvito.cards.core.Screen
import com.github.amvito.cards.details.presentation.CardUiCommunication

class CardsViewModel(
    runAsync: RunAsync,
    private val progressCommunication: ProgressCommunication,
    private val cardsInteractor: CardsInteractor,
    private val cardsUiStateCommunication: CardsUiStateCommunication,
    private val mapper: CardsResult.Mapper<CardUiState> = CardResultMapper(),
    private val detailsCommunication: CardUiCommunication,
    private val navigationCommunication: NavigationCommunication.Mutable,
) : BaseViewModel(runAsync) {

    fun fetchCards(cardsCount: Int) {
        progressCommunication.put(View.VISIBLE)
        handle({
            cardsInteractor.getCards(cardsCount)
        }) {
            progressCommunication.put(View.GONE)
            cardsUiStateCommunication.put(it.map(mapper))
        }
    }

    fun tryAgain(cardsCount: Int) {
        fetchCards(cardsCount)
    }

    fun showDetails(item: CardUi) {
        detailsCommunication.put(item)
        navigationCommunication.put(Navigation.Replace(Screen.CardDetailsFragment))
    }

    fun observeState(owner: LifecycleOwner, observe: Observer<CardUiState>) {
        cardsUiStateCommunication.observe(owner, observe)
    }

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) {
        progressCommunication.observe(owner, observer)
    }
}

class CardResultMapper : CardsResult.Mapper<CardUiState> {

    override fun map(errorMessage: String): CardUiState {
        return CardUiState.Fail(errorMessage)
    }

    override fun map(list: List<CardDomain>): CardUiState {
        return CardUiState.Success(list.map {
            it.map(CardDomain.Mapper.CardDomainToUi())
        })
    }
}

interface CardsUiStateCommunication : Communication.Mutable<CardUiState> {
    class Base : Communication.Ui<CardUiState>(), CardsUiStateCommunication
}

interface ProgressCommunication : Communication.Mutable<Int> {
    class Base : Communication.Ui<Int>(), ProgressCommunication
}