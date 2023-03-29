package com.example.weatherforecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

open class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val geoLocationDataResponse = MutableLiveData<NetworkResponse<List<Location>>>()
    private val weatherDataResponse = MutableLiveData<NetworkResponse<WeatherResponse>>()

    // Get Geo location of a city
    fun getGeoLocation(city: String) : LiveData<NetworkResponse<List<Location>>> {
        viewModelScope.launch {
            geoLocationDataResponse.postValue(repository.getGeoLocation(city))
        }
        return geoLocationDataResponse
    }

    // Get weather for a city
    fun getWeather(lat: Double, lon: Double) : LiveData<NetworkResponse<WeatherResponse>> {
        viewModelScope.launch {
            weatherDataResponse.postValue(repository.getWeather(lat, lon))
        }
        return weatherDataResponse
    }
}