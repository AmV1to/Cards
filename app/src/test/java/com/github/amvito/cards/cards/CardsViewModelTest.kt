package com.github.amvito.cards.cards

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.amvito.cards.cards.domain.CardDomain
import com.github.amvito.cards.cards.domain.CardsInteractor
import com.github.amvito.cards.cards.domain.CardsResult
import com.github.amvito.cards.cards.presentation.CardUi
import com.github.amvito.cards.cards.presentation.CardUiState
import com.github.amvito.cards.cards.presentation.CardsUiStateCommunication
import com.github.amvito.cards.cards.presentation.CardsViewModel
import com.github.amvito.cards.cards.presentation.ProgressCommunication
import com.github.amvito.cards.core.Communication
import com.github.amvito.cards.core.Navigation
import com.github.amvito.cards.core.NavigationCommunication
import com.github.amvito.cards.core.RunAsync
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
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

    @Before
    fun init() {
        progressCommunication = FakeProgressCommunication()
        fakeInteractor = FakeInteractor()
        uiStateCommunication = FakeCardsStateUiCommunication()
        fakeNavigation = FakeNavigation()
        fakeDetails = FakeDetails()
        viewModel = CardsViewModel(
            FakeRunAsync(),
            progressCommunication,
            fakeInteractor,
            uiStateCommunication,
            navigationCommunication = fakeNavigation,
            detailsCommunication = fakeDetails
        )
    }

    @Test
    fun test_init_first_run_success() = runBlocking {
        // init
        // show progress
        // get data
        // hide progress
        // show data
        // if we have a error
        // show success result
        // show success state
        fakeInteractor.changeResult(CardsResult.Success(listOf(CardDomain("a", "a", "a", "a"))))
        viewModel.fetchCards(1)

        assertEquals(2, progressCommunication.calledCount)
        // get success result

        assertEquals(
            CardsResult.Success(listOf(CardDomain("a", "a", "a", "a"))),
            fakeInteractor.takeCards(),
        )

        // get success state
        assertEquals(
            CardUiState.Success(listOf(CardUi("a", "a", "a", "a"))),
            uiStateCommunication.listOfState[0],
        )

        assertEquals(1, uiStateCommunication.calledCount)

    }

    @Test
    fun test_init_first_run_fail() = runBlocking {
        fakeInteractor.changeResult(CardsResult.Fail("exception"))
        viewModel.fetchCards(1)


        assertEquals(
            CardsResult.Fail("exception"),
            fakeInteractor.takeCards(),
        )

        assertEquals(2, progressCommunication.calledCount)

        assertEquals(
            CardUiState.Fail("exception"),
            uiStateCommunication.listOfState[0],
        )

        assertEquals(1, uiStateCommunication.calledCount)
    }

    @Test
    fun test_try_again_button_success() = runBlocking {
        fakeInteractor.changeResult(CardsResult.Success(listOf(CardDomain("a", "a", "a", "a"))))
        viewModel.tryAgain(1)


        assertEquals(2, progressCommunication.calledCount)


        assertEquals(
            CardsResult.Success(listOf(CardDomain("a", "a", "a", "a"))),
            fakeInteractor.takeCards(),
        )

        assertEquals(
            CardUiState.Success(listOf(CardUi("a", "a", "a", "a"))),
            uiStateCommunication.listOfState[0],
        )

        assertEquals(1, uiStateCommunication.calledCount)

    }

    @Test
    fun test_try_again_button_fail() = runBlocking {
        fakeInteractor.changeResult(CardsResult.Fail("exception"))
        viewModel.tryAgain(1)


        assertEquals(
            CardsResult.Fail("exception"),
            fakeInteractor.takeCards(),
        )

        assertEquals(2, progressCommunication.calledCount)

        assertEquals(
            CardUiState.Fail("exception"),
            uiStateCommunication.listOfState[0],
        )

        assertEquals(1, uiStateCommunication.calledCount)

    }

    @Test
    fun test_show_details() {
        val cardUi = CardUi("a", "a", "a", "a")

        viewModel.showDetails(cardUi)
        assertEquals(1, fakeDetails.calledCount)
        assertEquals(cardUi, fakeDetails.card!!)
        assertEquals(1, fakeNavigation.calledCount)
        assertEquals(true, fakeNavigation.navigation is Navigation.Replace)
    }
}

private class FakeCardsStateUiCommunication : CardsUiStateCommunication {

    var calledCount = 0
    val listOfState = mutableListOf<CardUiState>()

    override fun observe(owner: LifecycleOwner, observer: Observer<CardUiState>) = Unit

    override fun put(source: CardUiState) {
        listOfState.add(source)
        calledCount++
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

private class FakeDetails : Communication.Mutable<CardUi> {
    var calledCount = 0
    var card: CardUi? = null

    override fun observe(owner: LifecycleOwner, observer: Observer<CardUi>) {
    }

    override fun put(source: CardUi) {
        calledCount++
        card = source
    }

}

abstract class BaseTest {

    protected class FakeRunAsync : RunAsync {
        override fun <T : Any> runAsync(
            scope: CoroutineScope,
            block: suspend () -> T,
            ui: (T) -> Unit
        ) = runBlocking {
            block.invoke().let { ui.invoke(it) }
        }
    }
}