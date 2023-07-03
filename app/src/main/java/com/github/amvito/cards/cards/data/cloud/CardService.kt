package com.github.amvito.cards.cards.data.cloud

import retrofit2.http.GET
import retrofit2.http.Query

interface CardService {

    @GET("credit_cards")
    suspend fun getCards(@Query("_quantity")countCards:Int): ResponseFakerApi
}