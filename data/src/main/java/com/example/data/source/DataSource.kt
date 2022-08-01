package com.example.data.source

import com.example.network.model.responses.SportsEvents
import retrofit2.Response

interface DataSource {
    suspend fun getSport(): Response<List<SportsEvents>>

    interface Remote : DataSource {}

    interface Local : DataSource {}
}