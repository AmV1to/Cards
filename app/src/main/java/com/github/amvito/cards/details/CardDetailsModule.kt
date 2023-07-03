package com.github.amvito.cards.details

import com.github.amvito.cards.core.Module
import com.github.amvito.cards.core.ProvideCardDetailsCommunication
import com.github.amvito.cards.details.presentation.CardDetailsViewModel

class CardDetailsModule(
    private val provide: ProvideCardDetailsCommunication
) : Module<CardDetailsViewModel> {

    override fun viewModel(): CardDetailsViewModel {
        return CardDetailsViewModel(provide.provideDetails())
    }
}