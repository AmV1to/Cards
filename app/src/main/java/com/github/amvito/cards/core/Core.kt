package com.github.amvito.cards.core

import android.content.Context
import com.github.amvito.cards.cards.data.BaseCardsRepository
import com.github.amvito.cards.cards.data.ProvideDataSource
import com.github.amvito.cards.cards.data.ProvideService
import com.github.amvito.cards.cards.data.cloud.CardCloudDataSource
import com.github.amvito.cards.cards.data.cloud.CardService
import com.github.amvito.cards.cards.domain.CardsInteractor
import com.github.amvito.cards.cards.domain.CardsRepository
import com.github.amvito.cards.details.presentation.CardUiCommunication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Core : ProvideNavigation, ProvideRunAsync, ProvideDispatchers, ProvideCardsInteractor,
    ProvideCardRepository, ProvideDataSource, ProvideService, ProvideCardDetailsCommunication {

    class Base(
        private val context: Context,
    ) : Core {
        private val navigationCommunication = NavigationCommunication.Base()
        private val detailsCommunication = CardUiCommunication.Base()
        override fun navigation(): NavigationCommunication.Mutable {
            return navigationCommunication
        }

        override fun provideRunAsync(): RunAsync {
            return RunAsync.Base(provideDispatchers())
        }

        override fun provideDispatchers(): DispatchersList {
            return DispatchersList.Base()
        }

        override fun provideCardsInteractor(): CardsInteractor {
            return CardsInteractor.Base(
                cardsRepository = provideCardRepository(),
                handleException = HandleException.Base(ManageResources.Base(context))
            )
        }

        override fun provideCardRepository(): CardsRepository {
            return BaseCardsRepository(provideCloudDataSource(), HandleException.ThrowException())
        }

        override fun provideCloudDataSource(): CardCloudDataSource {
            return CardCloudDataSource.Base(provideService())
        }

        override fun provideOkHttp(): OkHttpClient {
            return OkHttpClient()
        }

        override fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .client(provideOkHttp())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
        }

        override fun provideDetails(): CardUiCommunication {
            return detailsCommunication
        }

        override fun provideService(): CardService {
            return provideRetrofit().create(CardService::class.java)
        }

        private companion object {
            const val BASE_URL = "https://fakerapi.it/api/v1/"
        }
    }
}

interface ProvideNavigation {
    fun navigation(): NavigationCommunication.Mutable
}

interface ProvideRunAsync {
    fun provideRunAsync(): RunAsync
}

interface ProvideDispatchers {
    fun provideDispatchers(): DispatchersList
}

interface ProvideCardsInteractor {
    fun provideCardsInteractor(): CardsInteractor
}

interface ProvideCardRepository {
    fun provideCardRepository(): CardsRepository
}

interface ProvideCardDetailsCommunication {
    fun provideDetails(): CardUiCommunication
}