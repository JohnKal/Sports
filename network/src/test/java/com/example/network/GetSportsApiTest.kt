package com.example.network

import androidx.test.filters.SmallTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertNotNull
import org.junit.Test

@SmallTest
class GetSportsApiTest : BaseNetworkTest() {

    private lateinit var mockedResponse: String

    override fun setUp() {
        setMockServerUrl()
        mockedResponse = MockResponseFileReader("raw/get_sports_response.json").content
    }

    @Test
    fun `given mock response with 200 code verify that getPerson response is not null or empty list`() {

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        runBlocking {
            val mockResponseList = mockService.getSports()

            assertNotNull(mockResponseList.body())
        }
    }

    override fun tearDown() {}

    override fun setMockServerUrl(): String = "api/sports/"
}