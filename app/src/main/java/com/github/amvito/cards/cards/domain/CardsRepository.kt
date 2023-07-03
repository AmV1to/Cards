package com.github.amvito.cards.cards.domain

import com.github.amvito.cards.cards.data.CardData

interface CardsRepository {
    suspend fun getCards(cardsCount: Int): List<CardData>
}