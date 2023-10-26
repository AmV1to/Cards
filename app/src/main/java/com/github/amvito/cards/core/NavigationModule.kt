package com.github.amvito.cards.core

import org.koin.core.qualifier.named
import org.koin.dsl.module

interface NavigationModule {
    fun provideMutable(): NavigationCommunication.Mutable

    fun providePut(): NavigationCommunication.Put

    fun provideObserve(): NavigationCommunication.Observe

    object ProvideNavigationCommunication : NavigationModule {
        private val navigationCommunication = NavigationCommunication.Base()

        override fun provideMutable(): NavigationCommunication.Mutable {
            return navigationCommunication
        }

        override fun providePut(): NavigationCommunication.Put {
            return navigationCommunication
        }

        override fun provideObserve(): NavigationCommunication.Observe {
            return navigationCommunication
        }
    }
}

val navigationModule = module {
    factory<NavigationCommunication.Mutable>(named("navigationCommunicationMutable")) {
        NavigationModule.ProvideNavigationCommunication.provideMutable()
    }

    factory<NavigationCommunication.Observe>(named("navigationCommunicationObserve")) {
        NavigationModule.ProvideNavigationCommunication.provideObserve()
    }

    factory<NavigationCommunication.Put>(named("navigationCommunicationPut")) {
        NavigationModule.ProvideNavigationCommunication.providePut()
    }
}
