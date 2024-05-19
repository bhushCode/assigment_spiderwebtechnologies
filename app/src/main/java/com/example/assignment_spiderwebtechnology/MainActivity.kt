package com.example.assignment_spiderwebtechnology

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.assignment_spiderwebtechnology.adapter.WeatherAdapter
import com.example.assignment_spiderwebtechnology.data.WeatherData
import com.example.assignment_spiderwebtechnology.databinding.ActivityMainBinding
import com.example.assignment_spiderwebtechnology.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    val API_KEY = "YOUR_API_HERE"
    val LOCATION = "london"
    val DAYS =1
    val ALERT = "yes"
    val AQI  ="yes"
    lateinit var arrayList: ArrayList<WeatherData.Forecast.Forecastday.Hour>
    lateinit var adapter:WeatherAdapter
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = DataBindingUtil.  setContentView(this,R.layout.activity_main)
        window.statusBarColor = getColor(R.color.background_color)

        weatherForecast(LOCATION)

        arrayList  = ArrayList()
        adapter = WeatherAdapter(arrayList, this@MainActivity)
        binding.idRVWetherRecyclerview.adapter=adapter
        binding.idIVSearch.setOnClickListener()
        {

             var loc = binding.idEDtCity.text.toString()

            if(!TextUtils.isEmpty(loc)){
                weatherForecast(loc)
            }
        }

    }

    fun weatherForecast( loc:String)
    {
        Retrofit.Builder()
            .baseUrl("http://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
            .getWeather(API_KEY,loc,DAYS,AQI,ALERT)
            .enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    if (response.isSuccessful) {
                        response.body()?.let {


                            Log.d("Hula",it.toString())

                            Glide.with(this@MainActivity).load("https:"+it.current.condition.icon).into(binding.idTVIcon)

                               binding.idTVCityName.text =  it.location.name
                                           binding.idTVCondition.text =         it.current.condition.text
                                binding.idTVTemperature.text = "${it.current.tempC}°c "

                                binding.txtWindspeed.text ="${it.current.windKph}/kph"
                                binding.txtAirQuality.text="${it.current.airQuality.co} co"
                            binding.txtHumidity.text = "${it.current.humidity}g.m-3"

                            arrayList.clear()
                           it.forecast.forecastday.forEach {
                               forecastday -> forecastday.hour.forEach { hour ->
                               arrayList.add(hour)
                           }
                           }


                            adapter.notifyDataSetChanged()
                        }
                    }

                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {

                    Log.d("Hula",t.message.toString())
                }
            })
    }
}