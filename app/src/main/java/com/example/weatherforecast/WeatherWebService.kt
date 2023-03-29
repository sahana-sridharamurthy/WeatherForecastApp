package com.example.weatherforecast

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface WeatherWebService {
    /**
     * Get geo location
     */
    @GET("geo/1.0/direct")
    suspend fun getGeoLocation(
        @Query("q") city: String,
    ): Response<List<Location>>

    /**
     * Get weather
     */
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
    ): Response<WeatherResponse>
}