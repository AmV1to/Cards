package com.github.amvito.cards.cards.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.amvito.cards.core.Communication
import com.github.amvito.cards.core.Navigation
import com.github.amvito.cards.core.NavigationCommunication
import com.github.amvito.cards.core.Screen

interface CardsCommunication : ObserveCards, CardDetails {

    fun showProgress(visibility: Int)
    fun showState(state: CardUiState)

    class Base(
        private val progressCommunication: ProgressCommunication,
        private val cardsUiStateCommunication: CardsUiStateCommunication,
        private val detailsCommunication: Communication.Put<CardUi>,
        private val navigationCommunication: NavigationCommunication.Mutable
    ) : CardsCommunication {
        override fun observeState(owner: LifecycleOwner, observe: Observer<CardUiState>) =
            cardsUiStateCommunication.observe(owner, observe)

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
            progressCommunication.observe(owner, observer)

        override fun showDetails(card: CardUi) {
            detailsCommunication.put(card)
            navigationCommunication.put(Navigation.Replace(Screen.CardDetailsFragment))
        }

        override fun showProgress(visibility: Int) {
            progressCommunication.put(visibility)
        }

        override fun showState(state: CardUiState) {
            cardsUiStateCommunication.put(state)
        }
    }
}