package com.github.amvito.cards.cards.domain

import com.github.amvito.cards.cards.data.CardData
import com.github.amvito.cards.core.HandleException

interface CardsInteractor {

    suspend fun getCards(cardsCount: Int): CardsResult

    class Base(
        private val cardsRepository: CardsRepository,
        private val handleException: HandleException<String>
    ): CardsInteractor {
        override suspend fun getCards(cardsCount: Int): CardsResult {
            return try {
                val result = cardsRepository.getCards(cardsCount)
                CardsResult.Success(result.map {
                    it.map(CardData.Mapper.CardDataToDomain())
                })
            } catch (e: Exception) {
                CardsResult.Fail(handleException.handle(e))
            }
        }
    }
}

