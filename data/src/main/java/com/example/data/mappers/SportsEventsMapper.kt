package com.example.data.mappers

import com.example.data.businessmodel.EventModel
import com.example.data.businessmodel.SportEventsModel
import com.example.data.businessmodel.SportType
import com.example.network.model.responses.SportsEvents
import javax.inject.Inject
import kotlin.reflect.typeOf

class SportsEventsMapper @Inject constructor(): Mapper<List<SportsEvents>, List<SportEventsModel>> {

    override fun map(input: List<SportsEvents>): List<SportEventsModel> =
        input.map { sport ->
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