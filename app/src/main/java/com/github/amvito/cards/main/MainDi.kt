package com.github.amvito.cards.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainDi = module {
    viewModel<MainViewModel> {
        MainViewModel(get(named("navigationCommunicationMutable")))
    }
}