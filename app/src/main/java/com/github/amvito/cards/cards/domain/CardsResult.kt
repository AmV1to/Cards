package com.github.amvito.cards.cards.domain


interface CardsResult {

    interface Mapper<T : Any> {
        fun map(list: List<CardDomain>): T
        fun map(errorMessage: String): T
    }

    fun <T: Any> map(mapper: Mapper<T>): T

    data class Success(
        private val list: List<CardDomain>
    ) : CardsResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.map(list)
        }
    }

    data class Fail(
        private val errorMessage: String,
    ) : CardsResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.map(errorMessage)
        }
    }
}