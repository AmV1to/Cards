package com.github.amvito.cards.cards.data

import com.github.amvito.cards.cards.data.cloud.CardCloudDataSource
import com.github.amvito.cards.cards.data.cloud.CardService
import okhttp3.OkHttpClient
import retrofit2.Retrofit


interface ProvideDataSource {
    fun provideCloudDataSource(): CardCloudDataSource
}

interface ProvideService {

    fun provideOkHttp(): OkHttpClient

    fun provideRetrofit(): Retrofit

    fun provideService(): CardService
}
