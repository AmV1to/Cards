package com.github.amvito.cards.cards.presentation

import android.view.View
import com.github.amvito.cards.cards.domain.CardsInteractor
import com.github.amvito.cards.cards.domain.CardsResult
import com.github.amvito.cards.core.BaseViewModel

interface HandleFetchCards {

    fun fetch(viewModel: BaseViewModel, cardsCount: Int)

    class Base(
        private val cardsInteractor: CardsInteractor,
        private val mapper: CardsResult.Mapper<CardUiState> = CardResultMapper(),
        private val cardsCommunication: CardsCommunication,
    ) : HandleFetchCards {
        override fun fetch(viewModel: BaseViewModel, cardsCount: Int) {
            cardsCommunication.showProgress(View.VISIBLE)
            viewModel.handle({
                cardsInteractor.getCards(cardsCount)
            }) {
                cardsCommunication.showProgress(View.GONE)
                cardsCommunication.showState(it.map(mapper))
            }
        }
    }
}