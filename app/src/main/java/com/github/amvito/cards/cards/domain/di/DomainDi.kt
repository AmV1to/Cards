package com.github.amvito.cards.cards.domain.di

import com.github.amvito.cards.cards.domain.CardsInteractor
import com.github.amvito.cards.core.HandleException
import org.koin.dsl.module

val domainModule = module {

    factory {
        HandleException.Base(get())
    }

    factory<CardsInteractor> {
        CardsInteractor.Base(get(), get())
    }
}