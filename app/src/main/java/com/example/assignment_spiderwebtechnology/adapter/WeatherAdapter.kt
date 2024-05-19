package com.example.assignment_spiderwebtechnology.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment_spiderwebtechnology.R
import com.example.assignment_spiderwebtechnology.data.WeatherData
import java.text.ParseException
import java.text.SimpleDateFormat

class WeatherAdapter(private  val arrayList: ArrayList<WeatherData.Forecast.Forecastday.Hour>, val context:Context):RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    class WeatherViewHolder(v: View):RecyclerView.ViewHolder(v)
    {
        val time: TextView = v.findViewById(R.id.idTVTime)
            val temperature:TextView=v.findViewById(R.id.idTVTemperature2)
            val image:ImageView = v.findViewById(R.id.idTVCondition)
            val windspeed:TextView = v.findViewById(R.id.idTVWindSpeed)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item_layout,parent,false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
  return arrayList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
      val list = arrayList.get(position)


        holder.windspeed.text ="${list.windKph}/kph"
        holder.temperature.text = "${list.tempC}°c"
        Glide.with(context).load("https:"+list.condition.icon).into(holder.image)
        val input = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val output = SimpleDateFormat("hh:mm aa")

        try {
            val t = input.parse(list.time)
            holder.time.setText(output.format(t))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}