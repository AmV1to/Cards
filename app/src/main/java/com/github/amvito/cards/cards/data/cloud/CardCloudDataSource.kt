package com.github.amvito.cards.cards.data.cloud

interface CardCloudDataSource {

    suspend fun getCards(countCards: Int): List<CardCloud>

    class Base(
        private val cardService: CardService,
    ) : CardCloudDataSource {
        override suspend fun getCards(countCards: Int): List<CardCloud> {
            val response = cardService.getCards(countCards)
            val data = response.data.map {
                CardCloud(it.expiration, it.number, it.owner, it.type)
            }
            return data
        }
    }
}