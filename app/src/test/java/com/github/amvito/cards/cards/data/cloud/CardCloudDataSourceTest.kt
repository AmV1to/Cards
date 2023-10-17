package com.github.amvito.cards.cards.data.cloud

import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CardCloudDataSourceTest {
    private lateinit var cloudDataSource: CardCloudDataSource
    private lateinit var fakeCardService: FakeCardService

    @Before
    fun setUp() {
        fakeCardService = FakeCardService()
        cloudDataSource = CardCloudDataSource.Base(
            fakeCardService
        )
    }

    @Test
    fun test_200() = runBlocking {
        fakeCardService.response =
            Response.success(ResponseFakerApi(200, listOf(Data("MasterCard", "2405579232388124", "04/26", "Al Bernhard")), "OK", 1))
        val actual = cloudDataSource.getCards(1)
        assertEquals(
            listOf(CardCloud("MasterCard", "2405579232388124", "04/26", "Al Bernhard")),
            actual
        )
    }


    @Test
    fun test_404():Unit = runBlocking{
        fakeCardService.response = Response.error(404, "{\"status\":\"Not found\", \"code\": 404, \"total\": 0}".toResponseBody(
            "application/json".toMediaType()
        ))

        try {
            fakeCardService.getCards(10)
        } catch (e: Exception) {
            assertEquals(ResourceNotFound(), e)
        }
    }
}

private class FakeCardService : CardService {
    var response: Response<ResponseFakerApi>? = null
    override suspend fun getCards(countCards: Int): Response<ResponseFakerApi> {
        return response!!
    }
}