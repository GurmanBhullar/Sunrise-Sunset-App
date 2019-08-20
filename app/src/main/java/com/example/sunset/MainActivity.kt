package com.example.sunset



import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL

import javax.net.ssl.HttpsURLConnection





class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    protected fun getsunset(view: View)
    {
        var city=name.text.toString()
        var url="https://api.openweathermap.org/data/2.5/weather?q=$city&APPID=dcb55462d1bbffe6b6b29ad46ba05e63"
        MyAsyncTask().execute(url)
    }
    inner class MyAsyncTask() : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
        }



        override fun doInBackground(vararg p0: String): String? {
            try {
                val url = URL(p0[0])
                val urlConnect = url.openConnection() as HttpsURLConnection
                urlConnect.connectTimeout = 7000
                var instring = ConvertStreamtoString(urlConnect.inputStream)
                publishProgress(instring)

            } catch (ex: Exception) {
            }
            return null
        }


override fun onProgressUpdate(vararg values: String?)
        {
            try {
                var json = JSONObject(values[0])


                var sys=json.getJSONObject("sys")
                var sunset=sys.getInt("sunset")
                var sunrise=sys.getInt("sunrise")

                val unixSeconds: Int = sunset

                val date = java.util.Date(unixSeconds * 1000L)

                val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")

                val formattedDate = sdf.format(date)

                val unixSeconds1: Int = sunrise

                val date1 = java.util.Date(unixSeconds1 * 1000L)

                val sdf1 = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")

                val formattedDate1 = sdf1.format(date1)


                view.setText(" Sunrise time is $formattedDate1 and Sunset time is $formattedDate")

            } catch (ex: Exception){
        }
        }


    }


    fun ConvertStreamtoString(inputStream: InputStream):String{
        val bufferreader= BufferedReader(InputStreamReader(inputStream))
        var line:String
        var allstring:String=""

        try {
            do {
                line=bufferreader.readLine()
                if(line!=null){
                    allstring+=line
                }
            }while (line!=null)
            inputStream.close()
        }catch (ex:Exception){}
        return allstring
    }



}
