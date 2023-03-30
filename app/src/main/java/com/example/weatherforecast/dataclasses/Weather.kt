package com.example.weatherforecast.dataclasses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "coord")
    val coordinates: Coordinates,

    @Json(name = "weather")
    val weatherList: List<Weather>?,

    @Json(name = "main")
    val main: Main,

    @Json(name = "wind")
    val wind: Wind,
)

@JsonClass(generateAdapter = true)
data class Coordinates(
    @Json(name = "lat")
    val lat: Double,

    @Json(name = "lon")
    val long: Double,
)

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "id")
    val id: String,

    @Json(name = "main")
    val main: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "icon")
    val icon: String,
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp")
    val temp: Double,

    @Json(name = "feels_like")
    val feelsLike: Double,

    @Json(name = "humidity")
    val humidity: Double,
)

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed")
    val speed: Double,
)
