package com.github.amvito.cards.cards

import com.github.amvito.cards.cards.presentation.CardsViewModel
import com.github.amvito.cards.core.Core
import com.github.amvito.cards.core.Module

class CardsModule(
    private val core: Core
) : Module<CardsViewModel> {

    override fun viewModel(): CardsViewModel {
        return CardsViewModel(
            runAsync = core.provideRunAsync(),
            handleFetchCards = core.provideHandleFetchCards(),
            cardsCommunication = core.provideCardsCommunication()
        )
    }
}