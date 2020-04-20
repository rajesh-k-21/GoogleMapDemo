package com.madlab.googlemapdemo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationData(var latitude: Double, var longitude: Double) : Parcelable