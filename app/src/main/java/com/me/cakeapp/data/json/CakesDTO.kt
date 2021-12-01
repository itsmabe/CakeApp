package com.me.cakeapp.data.json

import com.google.gson.annotations.SerializedName


class CakesDTO : ArrayList<CakeDTO>()

data class CakeDTO(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("desc")
    val desc: String? = null,
    @SerializedName("image")
    val image: String? = null
)