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
        // get cards repository
        // if success return listofcards data
        // else throw exception and handle thisOne

        val result = cardsInteractor.getCards(1)

        assertEquals(CardsResult.Success(listOf(CardDomain("a", "a", "a","a"))), result)
        assertEquals(1, fakeCardsRepository.calledCount)
    }

    @Test
    fun test_get_cards_fail() = runBlocking {
        fakeCardsRepository.exception = false
        val result = cardsInteractor.getCards(1)
        // check handle exception was caleed

        assertEquals(1, fakeCardsRepository.calledCount)
        assertEquals(1, fakeException.calledCount)
        assertEquals(CardsResult.Fail("a"), result)
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
            return listOf(CardData("a", "a", "a", "a"))
        } else {
            throw IllegalStateException("a")
        }
    }
}