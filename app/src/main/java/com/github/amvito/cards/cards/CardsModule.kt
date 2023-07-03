package com.github.amvito.cards.cards

import com.github.amvito.cards.cards.presentation.CardsUiStateCommunication
import com.github.amvito.cards.cards.presentation.CardsViewModel
import com.github.amvito.cards.cards.presentation.ProgressCommunication
import com.github.amvito.cards.core.Core
import com.github.amvito.cards.core.Module

class CardsModule(
    private val core: Core
) : Module<CardsViewModel> {

    override fun viewModel(): CardsViewModel {
        return CardsViewModel(
            runAsync = core.provideRunAsync(),
            progressCommunication = ProgressCommunication.Base(),
            cardsInteractor = core.provideCardsInteractor(),
            cardsUiStateCommunication = CardsUiStateCommunication.Base(),
            detailsCommunication = core.provideDetails(),
            navigationCommunication = core.navigation(),
        )
    }
}