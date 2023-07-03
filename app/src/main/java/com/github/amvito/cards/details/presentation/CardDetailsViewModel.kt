package com.github.amvito.cards.details.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.github.amvito.cards.cards.presentation.CardUi

class CardDetailsViewModel(
    private val communication: CardUiCommunication,
) : ViewModel() {

    fun observeCard(owner: LifecycleOwner, observer: Observer<CardUi>) {
        communication.observe(owner, observer)
    }
}