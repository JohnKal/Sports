package com.example.data.businessmodel

data class EventModel(
    val eventId: String = "",
    val sportId: String = "",
    val teamNames: String = "",
    val sh: String = "",
    val startGameTime: Number = -1,
    var isFav: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        when (other) {
            is EventModel -> {
                return this.eventId == other.eventId &&
                        this.sportId == other.sportId &&
                        this.teamNames == other.teamNames &&
                        this.sh == other.sh &&
                        this.startGameTime.toInt()  == other.startGameTime
            }
            else -> return false
        }
    }
}