package com.github.amvito.cards.cards.data.cloud

import com.github.amvito.cards.cards.data.CardData

data class CardCloud(
    private val expiration: String,
    private val number: String,
    private val owner: String,
    private val type: String
) {

    interface Mapper<T> {
        fun map(
            expiration: String,
            number: String,
            owner: String,
            type: String,
        ): T

        class CardCloudToData : Mapper<CardData> {
            override fun map(
                expiration: String,
                number: String,
                owner: String,
                type: String
            ): CardData{
                return CardData(expiration, number, owner, type)
            }
        }
    }

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.map(expiration, number, owner, type)
    }

}
