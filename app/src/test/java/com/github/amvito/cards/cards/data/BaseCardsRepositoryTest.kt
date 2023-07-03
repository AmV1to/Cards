package com.github.amvito.cards.cards.data

import com.github.amvito.cards.cards.data.cloud.CardCloud
import com.github.amvito.cards.cards.data.cloud.CardCloudDataSource
import com.github.amvito.cards.core.HandleException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BaseCardsRepositoryTest {


    private lateinit var repository: BaseCardsRepository
    private lateinit var cloudDataSource: FakeCardCloudDataSource
    private lateinit var handleException: HandleException<Exception>

    @Before
    fun init() {
        cloudDataSource = FakeCardCloudDataSource()
        handleException = FakeHandleException()
        repository = BaseCardsRepository(
            cloudDataSource,
            handleException
        )
    }

    @Test
    fun test_fetch_cards_success() = runBlocking {
        val result = repository.getCards(1)

        assertEquals(1, cloudDataSource.calledCount)
        assertEquals(result, listOf(CardData("a", "a", "a", "a")))
    }


    // todo create your own exceptions
    @Test(expected = IllegalStateException::class)
    fun test_fetch_cards_fail(): Unit = runBlocking {
        cloudDataSource.exception = true
        repository.getCards(1)
    }


}

private class FakeHandleException : HandleException<Exception> {
    override fun handle(e: Exception): Exception {
        return e
    }
}

private class FakeCardCloudDataSource : CardCloudDataSource {
    var calledCount = 0
    var exception = false
    override suspend fun getCards(countCards: Int): List<CardCloud> {
        calledCount++
        if (exception) {
            throw IllegalStateException()
        } else {
            return listOf(CardCloud("a", "a", "a", "a"))
        }
    }
}