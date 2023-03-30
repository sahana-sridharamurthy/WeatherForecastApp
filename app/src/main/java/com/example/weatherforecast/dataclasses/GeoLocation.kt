package com.example.weatherforecast.dataclasses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "lat")
    val lat: Double,

    @Json(name = "lon")
    val long: Double,

    @Json(name = "name")
    val name: String,
)