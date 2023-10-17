package com.github.amvito.cards.cards.domain

import com.github.amvito.cards.cards.data.CardData
import com.github.amvito.cards.core.HandleException
import com.github.amvito.cards.core.NoInternetConnection
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CardsInteractorTest {

    private lateinit var cardsInteractor: CardsInteractor
    private lateinit var fakeCardsRepository: FakeCardsRepository
    private lateinit var fakeException: FakeHandleExceptionString
    private lateinit var fakeMapper: FakeMapper


    @Before
    fun init() {
        fakeCardsRepository = FakeCardsRepository()
        fakeException = FakeHandleExceptionString()
        fakeMapper = FakeMapper()
        cardsInteractor = CardsInteractor.Base(
            fakeCardsRepository,
            fakeException,
            fakeMapper
        )
    }

    @Test
    fun test_get_cards_success() = runBlocking {

        val result = cardsInteractor.getCards(1)

        assertEquals(1, fakeMapper.calledCount)
        assertEquals(CardsResult.Success(listOf(CardDomain("MasterCard",
            "2405579232388124",
            "04/26",
            "Al Bernhard"))), result)
        assertEquals(1, fakeCardsRepository.calledCount)
    }

    @Test
    fun test_get_cards_fail_no_internet_connection() = runBlocking {
        fakeCardsRepository.exception = false
        fakeException.message ="No internet connection"
        val result = cardsInteractor.getCards(1)

        assertEquals(0, fakeMapper.calledCount)
        assertEquals(1, fakeCardsRepository.calledCount)
        assertEquals(1, fakeException.calledCount)
        assertEquals(CardsResult.Fail("No internet connection"), result)
    }

}


private class FakeHandleExceptionString : HandleException<String> {
    var calledCount = 0
    var message = ""
    override fun handle(e: Exception): String {
        calledCount++
        return message
    }

}

private class FakeMapper : CardData.Mapper<CardDomain> {
    var calledCount = 0
    override fun map(expiration: String, number: String, owner: String, type: String): CardDomain {
        calledCount++
        return CardDomain(expiration, number, owner, type)
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