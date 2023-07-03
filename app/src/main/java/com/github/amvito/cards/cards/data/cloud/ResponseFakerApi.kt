package com.github.amvito.cards.cards.data.cloud

data class ResponseFakerApi(
    val code: Int,
    val `data`: List<Data>,
    val status: String,
    val total: Int
)