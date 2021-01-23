package com.example.weatherapipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {

    companion object {
        var BaseURL = "http://api.openweathermap.org/"
        var appid = "18a1a16ff26a25bcea6aa824c657e300"
        var lat = "37"
        var lon = "127"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit : Retrofit? = Retrofit.Builder().baseUrl(BaseURL).addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit!!.create(WeatherService::class.java)
        val call = service.getCurrentAirPollutionData(lat, lon, appid)
        call.enqueue(object : Callback<WeatherResponse>{
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d("로그", "MainActivity - onFailure() called ${t.message}")
            }

            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if(response.code() == 200){
                    val weatherResponse = response.body()
                    Log.d("로그", "$response")
                    Log.d("로그", "MainActivity - onResponse() called")
                    Log.d("로그", "result : ${weatherResponse.toString()}")
                    Log.d("로그", "weatherresponse list ${weatherResponse!!.list}")
                    val strBuf =
                            "co : " + weatherResponse!!.list!![0].components!!.co + "\n" +
                            "no : " + weatherResponse.list!![0].components!!.no + "\n" +
                            "no2 : " + weatherResponse.list!![0].components!!.no2 + "\n" +
                            "o3 : " + weatherResponse.list!![0].components!!.o3 + "\n" +
                            "so2 : " + weatherResponse.list!![0].components!!.so2 + "\n" +
                            "pm25: " + weatherResponse.list!![0].components!!.pm2_5 + "\n" +
                            "pm10 : " + weatherResponse.list!![0].components!!.pm10 + "\n" +
                            "nh3 : " + weatherResponse.list!![0].components!!.nh3

                    findViewById<TextView>(R.id.text).text = strBuf
                }
            }
        })
    }
}

interface WeatherService {

    @GET("/data/2.5/air_pollution")
    fun getCurrentAirPollutionData(
            @Query("lat") lat : String,
            @Query("lon") lon : String,
            @Query("appid") appid: String
    ) : Call<WeatherResponse>

}

class WeatherResponse() {
    @SerializedName("list") var list : List<CompList>? = null
}

class CompList() {
    @SerializedName("components") var components : Components? = null
}

class Components() {
    @SerializedName("co") var co : Double? = null
    @SerializedName("no") var no : Double? = null
    @SerializedName("no2") var no2 : Double? = null
    @SerializedName("o3") var o3 : Double? = null
    @SerializedName("so2") var so2 : Double? = null
    @SerializedName("pm2_5") var pm2_5 : Double? = null
    @SerializedName("pm10") var pm10 : Double? = null
    @SerializedName("nh3") var nh3 : Double? = null
}