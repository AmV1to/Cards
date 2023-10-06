package com.github.amvito.cards.cards.data

import com.github.amvito.cards.cards.data.cloud.CardCloud
import com.github.amvito.cards.cards.data.cloud.CardCloudDataSource
import com.github.amvito.cards.core.HandleException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseCardsRepositoryTest {


    private lateinit var repository: BaseCardsRepository
    private lateinit var cloudDataSource: FakeCardCloudDataSource
    private lateinit var handleException: FakeHandleException

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
        assertEquals(result, listOf(CardData("MasterCard",
            "2405579232388124",
            "04/26",
            "Al Bernhard")))
    }

    @Test(expected = UnknownHostException::class)
    fun test_fetch_cards_fail_no_internet_connection(): Unit = runBlocking {
        cloudDataSource.exception = true
        repository.getCards(1)
        assertEquals(1, handleException.calledCount)
    }
}

private class FakeHandleException : HandleException<Exception> {
    var calledCount = 0
    override fun handle(e: Exception): Exception {
        calledCount++
        return e
    }
}

private class FakeCardCloudDataSource : CardCloudDataSource {
    var calledCount = 0
    var exception = false
    override suspend fun getCards(countCards: Int): List<CardCloud> {
        calledCount++
        if (exception) {
            throw UnknownHostException()
        } else {
            return listOf(CardCloud("MasterCard",
                "2405579232388124",
                "04/26",
                "Al Bernhard"))
        }
    }
}