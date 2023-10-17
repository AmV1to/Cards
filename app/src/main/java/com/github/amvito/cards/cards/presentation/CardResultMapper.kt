package com.github.amvito.cards.cards.presentation

import com.github.amvito.cards.cards.domain.CardDomain
import com.github.amvito.cards.cards.domain.CardsResult

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