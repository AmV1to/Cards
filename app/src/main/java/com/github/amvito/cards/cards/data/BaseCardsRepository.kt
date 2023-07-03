package com.github.amvito.cards.cards.data

import com.github.amvito.cards.cards.data.cloud.CardCloud
import com.github.amvito.cards.cards.data.cloud.CardCloudDataSource
import com.github.amvito.cards.cards.domain.CardsRepository
import com.github.amvito.cards.core.HandleException

class BaseCardsRepository(
    private val cloudDataSource: CardCloudDataSource,
    private val handleException: HandleException<Exception>,
    private val mapper: CardCloud.Mapper<CardData> = CardCloud.Mapper.CardCloudToData()
) : CardsRepository {
    override suspend fun getCards(cardsCount: Int): List<CardData> {
        return try {
            val result = cloudDataSource.getCards(cardsCount)
            result.map { card ->
                card.map(mapper)
            }
        } catch (e: Exception){
            throw handleException.handle(e)
        }
    }
}