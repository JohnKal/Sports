package com.example.data

import androidx.test.filters.SmallTest
import com.example.data.businessmodel.EventModel
import com.example.data.businessmodel.SportEventsModel
import com.example.data.businessmodel.SportType
import com.example.data.mappers.DataMappersFacade
import com.example.data.mappers.SportsEventsMapper
import com.example.data.repository.DataRepository
import com.example.data.repository.DataRepositoryImpl
import com.example.network.api.ServiceEndpoints
import com.example.network.helpers.NetworkHelper
import com.example.network.model.responses.SportsEvents
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import java.lang.reflect.Type

@SmallTest
@RunWith(MockitoJUnitRunner::class)
class DataRepositoryTest {

    private lateinit var mockedResponse: String
    private lateinit var mockedResponseModel: String

    @Mock
    lateinit var serviceEndpoints: ServiceEndpoints

    @InjectMocks
    lateinit var networkHelper: NetworkHelper

    lateinit var dataMappersFacade: DataMappersFacade

    @Mock
    lateinit var personDetailsMapper: SportsEventsMapper

    lateinit var dataRepository: DataRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockedResponse = MockResponseFileReader("raw/get_sports_response.json").content
        mockedResponseModel = MockResponseFileReader("raw/get_sports_response_model.json").content

        dataMappersFacade = DataMappersFacade(personDetailsMapper)
        dataRepository = DataRepositoryImpl(serviceEndpoints, networkHelper, dataMappersFacade)
    }

    @Test
    fun `given a mock sportsResponse verify that mapToDomainModel should return a non null domain model` () {
        val sportsList: Type = object : TypeToken<List<SportsEvents>?>() {}.type
        val mockedResponse: List<SportsEvents> = Gson().fromJson(mockedResponse, sportsList)

        `when`(dataMappersFacade.sportEventMapper.map(mockedResponse)).thenAnswer {
            mockedResponse.map { sport ->
                SportEventsModel(
                    sportType = SportType.valueOf(sport.i ?: ""),
                    title = sport.d ?: "",
                    events = sport.e.map { event ->
                        EventModel(
                            eventId = event.i ?: "",
                            sportId = event.si ?: "",
                            teamNames = event.d ?: "",
                            startGameTime = event.tt ?: -1
                        )
                    }
                )
            }
        }

        val sportsEventsModel = dataMappersFacade.sportEventMapper.map(mockedResponse)

        Assert.assertNotNull(sportsEventsModel)
    }

    @Test
    fun `given a mock response verify that getSports should map to domain model as expected`() = runBlocking {
        val sportsEventsList: Type = object : TypeToken<List<SportsEvents>?>() {}.type
        val mockedResponse: List<SportsEvents> = Gson().fromJson(mockedResponse, sportsEventsList)

        `when`(serviceEndpoints.getSports()).thenReturn(Response.success(mockedResponse))
        `when`(dataMappersFacade.sportEventMapper.map(mockedResponse)).thenAnswer {
            mockedResponse.map { sport ->
                SportEventsModel(
                    sportType = SportType.valueOf(sport.i ?: ""),
                    title = sport.d ?: "",
                    events = sport.e.map { event ->
                        EventModel(
                            eventId = event.i ?: "",
                            sportId = event.si ?: "",
                            teamNames = event.d ?: "",
                            startGameTime = event.tt ?: -1
                        )
                    }
                )
            }
        }

        val responseFromServerModel = dataRepository.getSportsEvents()

        verify(serviceEndpoints, times(1)).getSports()

        Assert.assertNotNull(responseFromServerModel)

        val sportsEventsModel: Type = object : TypeToken<List<SportEventsModel>?>() {}.type
        val mockedResponseModel: List<SportEventsModel> = Gson().fromJson(mockedResponseModel, sportsEventsModel)

        Assert.assertEquals(mockedResponseModel, responseFromServerModel)
    }

    @Test(expected = HttpException::class)
    fun `given an error response verify that getSportsDetails should throw HttpException`() = runBlocking {

        `when`(serviceEndpoints.getSports()).thenReturn(Response.error(401, "{}".toResponseBody()))

        given(dataRepository.getSportsEvents()).willThrow(HttpException(null))

        val response = dataRepository.getSportsEvents()

        Assert.assertNull(response)
    }
}