package com.github.amvito.cards.cards

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.amvito.cards.cards.domain.CardDomain
import com.github.amvito.cards.cards.domain.CardsInteractor
import com.github.amvito.cards.cards.domain.CardsResult
import com.github.amvito.cards.cards.presentation.CardUi
import com.github.amvito.cards.cards.presentation.CardUiState
import com.github.amvito.cards.cards.presentation.CardsCommunication
import com.github.amvito.cards.cards.presentation.CardsUiStateCommunication
import com.github.amvito.cards.cards.presentation.CardsViewModel
import com.github.amvito.cards.cards.presentation.HandleFetchCards
import com.github.amvito.cards.cards.presentation.ProgressCommunication
import com.github.amvito.cards.core.Navigation
import com.github.amvito.cards.core.NavigationCommunication
import com.github.amvito.cards.details.presentation.CardUiCommunication
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CardsViewModelTest : BaseTest() {

    private lateinit var viewModel: CardsViewModel
    private lateinit var progressCommunication: FakeProgressCommunication
    private lateinit var fakeInteractor: FakeInteractor
    private lateinit var uiStateCommunication: FakeCardsStateUiCommunication
    private lateinit var fakeNavigation: FakeNavigation
    private lateinit var fakeDetails: FakeDetails
    private lateinit var handleFetchCards: HandleFetchCards

    @Before
    fun init() {
        progressCommunication = FakeProgressCommunication()
        fakeInteractor = FakeInteractor()
        uiStateCommunication = FakeCardsStateUiCommunication()
        fakeNavigation = FakeNavigation()
        fakeDetails = FakeDetails()
        val cardsCommunication = CardsCommunication.Base(
            progressCommunication,
            uiStateCommunication,
            fakeDetails,
            fakeNavigation
        )
        handleFetchCards = HandleFetchCards.Base(
            cardsInteractor = fakeInteractor,
            cardsCommunication = cardsCommunication,
        )
        viewModel = CardsViewModel(
            runAsync = FakeRunAsync(),
            handleFetchCards = handleFetchCards,
            cardsCommunication
        )
    }

    @Test
    fun test_init_first_run_success() = runBlocking {
        fakeInteractor.changeResult(
            CardsResult.Success(
                listOf(
                    CardDomain(
                        "MasterCard",
                        "2405579232388124",
                        "04/26",
                        "Al Bernhard"
                    )
                )
            )
        )
        viewModel.fetchCards(1)

        assertEquals(2, progressCommunication.calledCount)
        // get success result

        assertEquals(
            CardsResult.Success(
                listOf(
                    CardDomain(
                        "MasterCard",
                        "2405579232388124",
                        "04/26",
                        "Al Bernhard"
                    )
                )
            ),
            fakeInteractor.takeCards(),
        )

        // get success state
        assertEquals(
            CardUiState.Success(
                listOf(
                    CardUi(
                        "MasterCard",
                        "2405579232388124",
                        "04/26",
                        "Al Bernhard"
                    )
                )
            ),
            uiStateCommunication.listOfState[0],
        )

        assertEquals(1, uiStateCommunication.listOfState.size)

    }

    @Test
    fun test_init_first_run_fail_no_internet_connection() = runBlocking {
        fakeInteractor.changeResult(CardsResult.Fail("No internet connection"))
        viewModel.fetchCards(1)


        assertEquals(
            CardsResult.Fail("No internet connection"),
            fakeInteractor.takeCards(),
        )

        assertEquals(2, progressCommunication.calledCount)

        assertEquals(
            CardUiState.Fail("No internet connection"),
            uiStateCommunication.listOfState[0],
        )

        assertEquals(1, uiStateCommunication.listOfState.size)
    }

    @Test
    fun test_try_again_button_success() = runBlocking {
        fakeInteractor.changeResult(
            CardsResult.Success(
                listOf(
                    CardDomain(
                        "MasterCard",
                        "2405579232388124",
                        "04/26",
                        "Al Bernhard"
                    )
                )
            )
        )
        viewModel.tryAgain(1)


        assertEquals(2, progressCommunication.calledCount)


        assertEquals(
            CardsResult.Success(
                listOf(
                    CardDomain(
                        "MasterCard",
                        "2405579232388124",
                        "04/26",
                        "Al Bernhard"
                    )
                )
            ),
            fakeInteractor.takeCards(),
        )

        assertEquals(
            CardUiState.Success(
                listOf(
                    CardUi(
                        "MasterCard",
                        "2405579232388124",
                        "04/26",
                        "Al Bernhard"
                    )
                )
            ),
            uiStateCommunication.listOfState[0],
        )

        assertEquals(1, uiStateCommunication.listOfState.size)

    }

    @Test
    fun test_try_again_button_fail() = runBlocking {
        fakeInteractor.changeResult(CardsResult.Fail("No internet connection"))
        viewModel.tryAgain(1)


        assertEquals(
            CardsResult.Fail("No internet connection"),
            fakeInteractor.takeCards(),
        )

        assertEquals(2, progressCommunication.calledCount)

        assertEquals(
            CardUiState.Fail("No internet connection"),
            uiStateCommunication.listOfState[0],
        )

        assertEquals(1, uiStateCommunication.listOfState.size)

    }

    @Test
    fun test_show_details() {
        val cardUi = CardUi("MasterCard", "2405579232388124", "04/26", "Al Bernhard")

        viewModel.showDetails(cardUi)
        assertEquals(1, fakeDetails.calledCount)
        assertEquals(cardUi, fakeDetails.card!!)
        assertEquals(1, fakeNavigation.calledCount)
        assertEquals(true, fakeNavigation.navigation is Navigation.Replace)
    }
}

private class FakeCardsStateUiCommunication : CardsUiStateCommunication {

    val listOfState = mutableListOf<CardUiState>()

    override fun observe(owner: LifecycleOwner, observer: Observer<CardUiState>) = Unit

    override fun put(source: CardUiState) {
        listOfState.add(source)
    }

}

private class FakeProgressCommunication : ProgressCommunication {

    var calledCount: Int = 0

    override fun observe(owner: LifecycleOwner, observer: Observer<Int>) = Unit

    override fun put(source: Int) {
        calledCount++
    }

}


private class FakeInteractor : CardsInteractor {

    private var result: CardsResult? = null

    fun changeResult(newResult: CardsResult) {
        result = newResult
    }

    fun takeCards() = result!!

    override suspend fun getCards(cardsCount: Int): CardsResult {
        return result!!
    }

}

private class FakeNavigation : NavigationCommunication.Mutable {

    var calledCount = 0
    var navigation: Navigation? = null
    override fun observe(owner: LifecycleOwner, observer: Observer<Navigation>) {
    }

    override fun put(source: Navigation) {
        calledCount++
        navigation = source
    }

}

private class FakeDetails : CardUiCommunication {
    var calledCount = 0
    var card: CardUi? = null

    override fun observe(owner: LifecycleOwner, observer: Observer<CardUi>) = Unit

    override fun put(source: CardUi) {
        calledCount++
        card = source
    }

}