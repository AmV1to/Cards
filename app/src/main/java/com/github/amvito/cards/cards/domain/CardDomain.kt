package com.github.amvito.cards.cards.domain

import com.github.amvito.cards.cards.presentation.CardUi

data class CardDomain(
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

        class CardDomainToUi : Mapper<CardUi> {
            override fun map(
                expiration: String,
                number: String,
                owner: String,
                type: String
            ): CardUi {
                return CardUi(expiration, number, owner, type)
            }
        }
    }

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.map(expiration, number, owner, type)
    }
}