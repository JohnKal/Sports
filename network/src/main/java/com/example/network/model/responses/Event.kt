package com.example.network.model.responses

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("d"  ) var d: String? = null,
    @SerializedName("i"  ) var i: String? = null,
    @SerializedName("si" ) var si: String? = null,
    @SerializedName("sh" ) var sh: String? = null,
    @SerializedName("tt" ) var tt: Int? = null
)