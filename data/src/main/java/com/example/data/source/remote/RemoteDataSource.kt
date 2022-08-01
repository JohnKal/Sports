package com.example.data.source.remote

import com.example.data.source.DataSource
import com.example.network.api.ServiceEndpoints
import com.example.network.model.responses.SportsEvents
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    val serviceEndpoints: ServiceEndpoints
): DataSource.Remote {

    override suspend fun getSport(): Response<List<SportsEvents>> =
        serviceEndpoints.getSports()
}