package com.me.cakeapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cake(
    val title: String? = null,
    val desc: String? = null,
    val image: String? = null
) : Parcelable