package com.github.amvito.cards.cards.domain

import com.github.amvito.cards.cards.data.CardData
import com.github.amvito.cards.core.HandleException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CardsInteractorTest {

    private lateinit var cardsInteractor: CardsInteractor
    private lateinit var fakeCardsRepository: FakeCardsRepository
    private lateinit var fakeException: FakeHandleExceptionString


    @Before
    fun init() {
        fakeCardsRepository = FakeCardsRepository()
        fakeException = FakeHandleExceptionString()

        cardsInteractor = CardsInteractor.Base(
            fakeCardsRepository,
            fakeException,
        )
    }

    @Test
    fun test_get_cards_success() = runBlocking {

        val result = cardsInteractor.getCards(1)

        assertEquals(CardsResult.Success(listOf(CardDomain("MasterCard",
            "2405579232388124",
            "04/26",
            "Al Bernhard"))), result)
        assertEquals(1, fakeCardsRepository.calledCount)
    }

    @Test
    fun test_get_cards_fail_no_internet_connection() = runBlocking {
        fakeCardsRepository.exception = false
        val result = cardsInteractor.getCards(1)

        assertEquals(1, fakeCardsRepository.calledCount)
        assertEquals(1, fakeException.calledCount)
        assertEquals(CardsResult.Fail("No internet connection"), result)
    }

}


private class FakeHandleExceptionString : HandleException<String> {
    var calledCount = 0

    override fun handle(e: Exception): String {
        calledCount++
        return "a"
    }

}

private class FakeCardsRepository : CardsRepository {
    var calledCount = 0
    var exception = true

    override suspend fun getCards(cardsCount: Int): List<CardData> {
        calledCount++
        if (exception) {
            return listOf(CardData("MasterCard",
                "2405579232388124",
                "04/26",
                "Al Bernhard"))
        } else {
            throw NoInternetConnection()
        }
    }
}