package com.github.amvito.cards.details.presentation.di

import com.github.amvito.cards.details.presentation.CardDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsDi = module {
    viewModel {
        CardDetailsViewModel(get())
    }
}