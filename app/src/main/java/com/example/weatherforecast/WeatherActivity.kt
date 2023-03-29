package com.example.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_weather.*
import javax.inject.Inject

class WeatherActivity : AppCompatActivity() {

    @Inject
    lateinit var mViewModelFactory: WeatherViewModelFactory

    private val mViewModel: WeatherViewModel by lazy {
        ViewModelProvider(
            this@WeatherActivity,
            mViewModelFactory
        ).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        supportActionBar?.hide()

        DaggerCoreComponent.builder().coreModule(CoreModule(this@WeatherActivity)).build()
            .injectWeatherActivity(this@WeatherActivity)

        bt_search.setOnClickListener {
            if(et_city_name.text.isNullOrEmpty()) {
                Toast.makeText(this, "City name is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val geoLocation = mViewModel.getGeoLocation(et_city_name.text.toString())
            geoLocation.observe(this@WeatherActivity, Observer { response ->
                when (response) {
                    is NetworkResponse.Success -> {
                        val data = response.data
                        val weather = mViewModel.getWeather(data[0].lat, data[0].long)
                        weather.observe(this@WeatherActivity, Observer { response ->
                            when (response) {
                                is NetworkResponse.Success -> {
                                    setWeather(response.data)
                                }
                                is NetworkResponse.Failure -> {
                                    Toast.makeText(this@WeatherActivity, "API error", Toast.LENGTH_SHORT).show()
                                }
                            }
                        })
                    }
                    is NetworkResponse.Failure -> {
                        Toast.makeText(this@WeatherActivity, "API error", Toast.LENGTH_SHORT).show()
                    }
                }
            })


        }

    }

    private fun setWeather(data: WeatherResponse) {
        tv_description.text = data.weatherList?.get(0)?.description?.uppercase()
        tv_temp.text = "Temp: ${data.main.temp} C"
        tv_feels.text = "Feels like: ${data.main.temp} C"
        tv_humidity.text = "Humidity: ${data.main.temp}%"
        tv_wind_speed.text = "Wind: ${data.main.temp} kmph"

        val link = "https://openweathermap.org/img/wn/${data.weatherList?.get(0)?.icon}@2x.png"
        Glide.with(this@WeatherActivity)
            .load(link)
            .into(iv_icon)
    }
}