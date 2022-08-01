package com.example.data.repository

import com.example.data.businessmodel.SportEventsModel
import com.example.data.mappers.DataMappersFacade
import com.example.network.api.ServiceEndpoints
import com.example.network.helpers.NetworkHelper
import com.example.network.helpers.ResultWrapper
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class DataRepositoryImpl @Inject constructor(
    val serviceEndpoints: ServiceEndpoints,
    val networkHelper: NetworkHelper,
    val dataMappersFacade: DataMappersFacade
) : DataRepository {

    override suspend fun getSports(): ResultWrapper<List<SportEventsModel>?> =
        withContext(coroutineContext) {
            networkHelper.safeApiCall {
                getSportsEvents()
            }
        }


    override suspend fun getSportsEvents(): List<SportEventsModel>? {
        val response = serviceEndpoints.getSports()

        if (!response.isSuccessful)
            throw HttpException(response)

        return response.body()?.let {
            dataMappersFacade.sportEventMapper.map(it)
        }
    }
}