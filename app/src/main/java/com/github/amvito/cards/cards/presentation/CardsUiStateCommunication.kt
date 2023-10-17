package com.github.amvito.cards.cards.presentation

import com.github.amvito.cards.core.Communication

interface CardsUiStateCommunication : Communication.Mutable<CardUiState> {
    class Base : Communication.Ui<CardUiState>(), CardsUiStateCommunication
}