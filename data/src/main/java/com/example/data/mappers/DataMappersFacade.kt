package com.example.data.mappers

import javax.inject.Inject

class DataMappersFacade @Inject constructor(
    val sportEventMapper: SportsEventsMapper
)