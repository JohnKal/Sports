package com.example.network.api

import com.example.network.model.responses.SportsEvents
import retrofit2.Response
import retrofit2.http.GET

interface ServiceEndpoints {

    @GET("api/sports")
    suspend fun getSports(): Response<List<SportsEvents>>
}