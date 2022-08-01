package com.example.network.model.responses

import com.google.gson.annotations.SerializedName

data class SportsEvents(
    @SerializedName("i" ) var i : String? = null,
    @SerializedName("d" ) var d : String? = null,
    @SerializedName("e" ) var e : ArrayList<Event> = arrayListOf()
)