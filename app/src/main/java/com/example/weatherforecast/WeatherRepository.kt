package com.example.weatherforecast

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class WeatherRepository(private val webService: WeatherWebService,
                                    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {
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