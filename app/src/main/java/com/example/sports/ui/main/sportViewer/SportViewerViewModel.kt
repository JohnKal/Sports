package com.example.sports.ui.main.sportViewer

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.businessmodel.SportEventsModel
import com.example.data.repository.DataRepository
import com.example.network.helpers.ResultWrapper
import com.example.sports.ui.main.sportViewer.state.SportsEventsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportViewerViewModel @Inject constructor(
    private val isForTesting: Boolean = false,
    private val dataRepository: DataRepository
) : ViewModel() {

    private val sportsEventsState: MutableLiveData<SportsEventsState> = MutableLiveData()

    fun getSportsEventState(): LiveData<SportsEventsState> = sportsEventsState

    init {
        if (!isForTesting) {
            getSports()
        }
    }

    @VisibleForTesting
    fun getSports() = viewModelScope.launch {

        sportsEventsState.value = SportsEventsState.Loading

        val sportsEventsResponse = async { dataRepository.getSports() }

        when (sportsEventsResponse.await()) {
            is ResultWrapper.NetworkError -> sportsEventsState.value = SportsEventsState.Error()
            is ResultWrapper.GenericError -> sportsEventsState.value = SportsEventsState.Error(
                (sportsEventsResponse.await() as ResultWrapper.GenericError).code,
                (sportsEventsResponse.await() as ResultWrapper.GenericError).error
            )
            is ResultWrapper.Success -> sportsEventsState.value = SportsEventsState.Success(
                (sportsEventsResponse.await() as ResultWrapper.Success<List<SportEventsModel>?>).value
            )
        }
    }

}