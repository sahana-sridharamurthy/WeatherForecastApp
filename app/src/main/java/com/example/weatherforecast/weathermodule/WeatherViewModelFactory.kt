package com.example.weatherforecast.weathermodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository) as T
        } else {
            throw IllegalArgumentException("view model not available")
        }
    }
}