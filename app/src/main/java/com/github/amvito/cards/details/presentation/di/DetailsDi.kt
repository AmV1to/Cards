package com.github.amvito.cards.details.presentation.di

import com.github.amvito.cards.details.presentation.CardDetailsViewModel
import com.github.amvito.cards.details.presentation.CardUiCommunication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

interface CardsCommunicationModule {

    fun providePut(): CardUiCommunication.Put

    fun provideObserve(): CardUiCommunication.Observe

    fun provideMutable(): CardUiCommunication.Mutable

    object ProvideCardUiCommunication : CardsCommunicationModule {
        private val cardUi = CardUiCommunication.Base()
        override fun providePut(): CardUiCommunication.Put {
            return cardUi
        }

        override fun provideObserve(): CardUiCommunication.Observe {
            return cardUi
        }

        override fun provideMutable(): CardUiCommunication.Mutable {
            return cardUi
        }
    }
}

val detailsDi = module {
    viewModel {
        CardDetailsViewModel(get(named("detailsCommunicationObserve")))
    }

    factory<CardUiCommunication.Put>(named("detailsCommunicationPut")) {
        CardsCommunicationModule.ProvideCardUiCommunication.providePut()
    }

    factory<CardUiCommunication.Observe>(named("detailsCommunicationObserve")) {
        CardsCommunicationModule.ProvideCardUiCommunication.provideObserve()
    }
}
