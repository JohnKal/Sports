package com.example.sports.ui.main.sportViewer.state

import com.example.data.businessmodel.SportEventsModel
import com.example.network.model.responses.ApiErrorResponse

sealed class SportsEventsState : RenderState {
    object Loading : SportsEventsState()
    data class Success(val listSportsEventsModel: List<SportEventsModel>?) : SportsEventsState()
    data class Error(val code: Int? = null, val error: ApiErrorResponse? = null) : SportsEventsState() }