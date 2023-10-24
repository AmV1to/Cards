package com.github.amvito.cards.details.presentation.di

import com.github.amvito.cards.details.presentation.CardDetailsViewModel
import com.github.amvito.cards.details.presentation.CardUiCommunication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val detailsDi = module {
    viewModel {
        CardDetailsViewModel(get(named("detailsCommunication")))
    }

    single<CardUiCommunication>(named("detailsCommunication")) {
        CardUiCommunication.Base()
    }
}