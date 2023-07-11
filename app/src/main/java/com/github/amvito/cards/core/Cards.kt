package com.github.amvito.cards.core

import android.app.Application
import com.github.amvito.cards.cards.presentation.di.cardsModule
import com.github.amvito.cards.cards.data.di.dataModule
import com.github.amvito.cards.cards.domain.di.domainModule
import com.github.amvito.cards.details.presentation.di.detailsDi
import com.github.amvito.cards.main.mainDi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Cards : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Cards)
            modules(listOf(mainDi, detailsDi, cardsModule, domainModule, dataModule))
        }
    }
}