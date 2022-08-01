package com.example.sports

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.data.businessmodel.SportEventsModel
import com.example.data.repository.DataRepositoryImpl
import com.example.network.helpers.ResultWrapper
import com.example.sports.ui.main.sportViewer.SportViewerViewModel
import com.example.sports.ui.main.sportViewer.state.SportsEventsState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestRule
import java.lang.reflect.Type


@ExtendWith(MockKExtension::class)
@SmallTest
class ViewModelsTest {

    private lateinit var viewModel: SportViewerViewModel
    val dispatcher = TestCoroutineDispatcher()

    private lateinit var mockedResponseModel: String

    @MockK
    private lateinit var dataRepository: DataRepositoryImpl

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)

        mockedResponseModel = MockResponseFileReader("raw/get_sports_response_model.json").content

        viewModel = SportViewerViewModel(true, dataRepository)

        coEvery {
            runBlocking {
                dataRepository.getSports()
            }
        } returns ResultWrapper.GenericError()
    }

    @Test
    fun `given Success from data repository verify that getSports should change sportsEventsState to Success`()  {

        val sportsEventsModel: Type = object : TypeToken<List<SportEventsModel>?>() {}.type
        val mockedResponseModel: List<SportEventsModel> = Gson().fromJson(mockedResponseModel, sportsEventsModel)

        coEvery {
            runBlocking {
                dataRepository.getSports()
            }
        } returns ResultWrapper.Success(mockedResponseModel)

        viewModel.getSports()

        verify(exactly = 1) { runBlocking { dataRepository.getSports() } }

        Assert.assertEquals(viewModel.getSportsEventState().value, SportsEventsState.Success(mockedResponseModel))
    }

    @Test
    fun `given GenericError from data repository verify that getSports should change sportsEventsState to Error`()  {

        val genericError = ResultWrapper.GenericError(401, null)

        coEvery {
            runBlocking {
                dataRepository.getSports()
            }
        } returns genericError

        viewModel.getSports()

        verify(exactly = 1) { runBlocking { dataRepository.getSports() } }

        Assert.assertEquals(viewModel.getSportsEventState().value, SportsEventsState.Error(genericError.code, genericError.error))
    }

    @Test
    fun `given NetworkError from data repository verify that getSports should change sportsEventsState to Error`()  {

        val networkError = ResultWrapper.NetworkError

        coEvery {
            runBlocking {
                dataRepository.getSports()
            }
        } returns networkError

        viewModel.getSports()

        verify(exactly = 1) { runBlocking { dataRepository.getSports() } }

        Assert.assertEquals(viewModel.getSportsEventState().value, SportsEventsState.Error())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}