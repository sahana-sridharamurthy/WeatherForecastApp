package com.example.weatherforecast.weathermodule

import com.example.weatherforecast.dataclasses.Location
import com.example.weatherforecast.common.NetworkResponse
import com.example.weatherforecast.dataclasses.WeatherResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class which provides geo location and weather dat.
 */
open class WeatherRepository(private val webService: WeatherWebService,
                             private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    /**
     * Fetch geo location data
     */
    suspend fun getGeoLocation(city: String): NetworkResponse<List<Location>> {
        return withContext(ioDispatcher){
            val response = webService.getGeoLocation(city)
            if (response.isSuccessful) {
                return@withContext NetworkResponse.success(response.body()!!)
            } else {
                return@withContext NetworkResponse.failure<List<Location>>(Throwable("Something went wrong, Please try again later."))
            }
        }
    }

    /**
     * Fetch weather data
     */
    suspend fun getWeather(lat: Double, lon: Double): NetworkResponse<WeatherResponse> {
        return withContext(ioDispatcher){
            val response = webService.getWeather(lat, lon)
            if (response.isSuccessful) {
                return@withContext NetworkResponse.success(response.body()!!)
            } else {
                return@withContext NetworkResponse.failure<WeatherResponse>(Throwable("Something went wrong, Please try again later."))
            }
        }
    }
}