package com.github.amvito.cards.cards.data.di

import com.github.amvito.cards.cards.data.BaseCardsRepository
import com.github.amvito.cards.cards.data.HandleDomainException
import com.github.amvito.cards.cards.data.cloud.CardCloudDataSource
import com.github.amvito.cards.cards.data.cloud.CardService
import com.github.amvito.cards.cards.domain.CardsRepository
import com.github.amvito.cards.core.CardException
import com.github.amvito.cards.core.HandleException
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceProvider {
    fun <T : Any> provideService(clazz: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(clazz)
    }

    private const val BASE_URL = "https://fakerapi.it/api/v1/"
}

val dataModule = module {

    single<CardService> {
        ServiceProvider.provideService(CardService::class.java)
    }

    factory<HandleException<CardException>> {
        HandleDomainException()
    }

    factory<CardCloudDataSource> {
        CardCloudDataSource.Base(cardService = get())
    }

    factory<CardsRepository> {
        BaseCardsRepository(
            cloudDataSource = get(),
            handleException = get()
        )
    }

}