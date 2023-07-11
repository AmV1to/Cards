package com.github.amvito.cards.cards.presentation.di

import com.github.amvito.cards.cards.presentation.CardsUiStateCommunication
import com.github.amvito.cards.cards.presentation.CardsViewModel
import com.github.amvito.cards.cards.presentation.ProgressCommunication
import com.github.amvito.cards.core.DispatchersList
import com.github.amvito.cards.core.ManageResources
import com.github.amvito.cards.core.NavigationCommunication
import com.github.amvito.cards.core.RunAsync
import com.github.amvito.cards.details.presentation.CardUiCommunication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cardsModule = module {

    single<ManageResources> {
        ManageResources.Base(get())
    }

    single<RunAsync> {
        RunAsync.Base(DispatchersList.Base())
    }

    single<CardUiCommunication> {
        CardUiCommunication.Base()
    }

    single<NavigationCommunication.Mutable> {
        NavigationCommunication.Base()
    }

    factory<ProgressCommunication> {
        ProgressCommunication.Base()
    }

    factory<CardsUiStateCommunication> {
        CardsUiStateCommunication.Base()
    }

    viewModel<CardsViewModel> {
        CardsViewModel(
            runAsync = get(),
            progressCommunication = get(),
            cardsInteractor = get(),
            cardsUiStateCommunication = get(),
            detailsCommunication = get(),
            navigationCommunication = get()
        )
    }
}