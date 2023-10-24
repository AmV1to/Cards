package com.github.amvito.cards.cards.data.cloud

interface CardCloudDataSource {

    suspend fun getCards(countCards: Int): List<CardCloud>

    class Base(
        private val cardService: CardService,
    ) : CardCloudDataSource {
        override suspend fun getCards(countCards: Int): List<CardCloud> {
            val response = cardService.getCards(countCards)
            if (response.code() == 200) {
                return response.body()?.data?.map {
                    CardCloud(it.expiration, it.number, it.owner, it.type)
                }!!
            }
            throw ResourceNotFound()
        }
    }
}

class ResourceNotFound() : Exception()
