package com.github.amvito.cards.cards.presentation.di

import com.github.amvito.cards.cards.presentation.CardsCommunication
import com.github.amvito.cards.cards.presentation.CardsUiStateCommunication
import com.github.amvito.cards.cards.presentation.CardsViewModel
import com.github.amvito.cards.cards.presentation.HandleFetchCards
import com.github.amvito.cards.cards.presentation.ProgressCommunication
import com.github.amvito.cards.core.DispatchersList
import com.github.amvito.cards.core.ManageResources
import com.github.amvito.cards.core.RunAsync
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val cardsModule = module {

    single<ManageResources> {
        ManageResources.Base(context = get())
    }

    single<RunAsync> {
        RunAsync.Base(dispatchersList = DispatchersList.Base())
    }

    single<CardsCommunication> {
        CardsCommunication.Base(
            progressCommunication = ProgressCommunication.Base(),
            cardsUiStateCommunication = CardsUiStateCommunication.Base(),
            detailsCommunication = get(named("detailsCommunicationPut")),
            navigationCommunication = get(named("navigationCommunicationPut")),
        )
    }


    factory<HandleFetchCards> {
        HandleFetchCards.Base(
            cardsInteractor = get(),
            cardsCommunication = get(),
        )
    }

    viewModel<CardsViewModel> {
        CardsViewModel(
            runAsync = get(),
            handleFetchCards = get(),
            cardsCommunication = get()
        )
    }
}