package com.github.amvito.cards.cards.data

import com.github.amvito.cards.cards.domain.CardDomain

data class CardData(
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

        class CardDataToDomain : Mapper<CardDomain> {
            override fun map(
                expiration: String,
                number: String,
                owner: String,
                type: String
            ): CardDomain {
                return CardDomain(expiration, number, owner, type)
            }
        }
    }

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.map(expiration, number, owner, type)
    }
//    fun map(): CardDomain {
//        return CardDomain(data)
//    }
}