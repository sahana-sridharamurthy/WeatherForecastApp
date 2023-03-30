package com.example.weatherforecast.weathermodule

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.weatherforecast.common.NetworkResponse
import com.example.weatherforecast.dataclasses.Location
import com.example.weatherforecast.dataclasses.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.*
import retrofit2.Response


class WeatherViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()

    @Rule
    var rule: TestRule = InstantTaskExecutorRule()

    /**
     * This method is used to initialise assumed variables before tests
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        // Do nothing else
    }

    /**
     * This method is used to clear assumed variables after tests
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        // Do nothing else
    }

    @Test
    fun getGeoLocation() {
        runBlocking {
            val city = "Bangalore"
            val webService = mock(WeatherWebService::class.java)
            `when`(webService.getGeoLocation(city)).thenReturn(Response.success(emptyList()))
            val repository = WeatherRepository(webService, Dispatchers.IO)
            val viewModel = WeatherViewModel(repository)

            val observer = Observer<NetworkResponse<List<Location>>> { response ->
                assertThat(response, instanceOf(NetworkResponse.Success::class.java))
            }

            viewModel.getGeoLocation(city).observeForever(observer)
        }
    }

    @Test
    fun getWeather() {
        runBlocking {
            val lat = 0.0
            val lon = 0.0
            val response = mock(WeatherResponse::class.java)
            val webService = mock(WeatherWebService::class.java)
            `when`(webService.getWeather(lat, lon)).thenReturn(Response.success(response))
            val repository = WeatherRepository(webService, Dispatchers.IO)
            val viewModel = WeatherViewModel(repository)

            val observer = Observer<NetworkResponse<WeatherResponse>> { response ->
                assertThat(response, instanceOf(NetworkResponse.Success::class.java))
            }

            viewModel.getWeather(lat, lon).observeForever(observer)
        }
    }
}
