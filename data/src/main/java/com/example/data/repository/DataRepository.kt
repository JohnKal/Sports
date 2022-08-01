package com.example.data.repository

import com.example.data.businessmodel.SportEventsModel
import com.example.network.helpers.ResultWrapper

interface DataRepository {

    /**
     * Get sports
     */
    suspend fun getSports(): ResultWrapper<List<SportEventsModel>?>
    suspend fun getSportsEvents(): List<SportEventsModel>?
}