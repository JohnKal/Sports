package com.example.data.mappers

import com.example.data.businessmodel.EventModel
import com.example.data.businessmodel.SportEventsModel
import com.example.data.businessmodel.SportType
import com.example.network.model.responses.SportsEvents
import javax.inject.Inject

class SportsEventsMapper @Inject constructor(): Mapper<List<SportsEvents>, List<SportEventsModel>> {

    override fun map(input: List<SportsEvents>): List<SportEventsModel> =
        mapList(input) { sport ->
            SportEventsModel(
                sportType = SportType.valueOf(sport.i ?: ""),
                title = sport.d ?: "",
                events = mapList(sport.e) { event ->
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