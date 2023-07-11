package com.github.amvito.cards.cards.data.di

import com.github.amvito.cards.cards.data.BaseCardsRepository
import com.github.amvito.cards.cards.data.cloud.CardCloudDataSource
import com.github.amvito.cards.cards.data.cloud.CardService
import com.github.amvito.cards.cards.domain.CardsRepository
import com.github.amvito.cards.core.HandleException
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceProvider {
    fun provideService(): CardService {
        val retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(CardService::class.java)
    }

    private const val BASE_URL = "https://fakerapi.it/api/v1/"
}

val dataModule = module {

    single {
        ServiceProvider.provideService()
    }

    factory<HandleException<Exception>> {
        HandleException.ThrowException()
    }

    factory<CardCloudDataSource> {
        CardCloudDataSource.Base(get())
    }

    factory<CardsRepository> {
        BaseCardsRepository(get(), get())
    }

}