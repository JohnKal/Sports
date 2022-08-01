package com.example.data.businessmodel

data class SportEventsModel(
    val sportType: SportType,
    val title: String,
    val events: List<EventModel>
) {
    override fun equals(other: Any?): Boolean {
        when (other) {
            is SportEventsModel -> {
                return this.sportType == other.sportType &&
                        this.title == other.title &&
                        this.events == other.events
            }
            else -> return false
        }
    }
}