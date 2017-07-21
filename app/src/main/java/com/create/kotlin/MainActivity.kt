package com.create.kotlin

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.net.URL


class MainActivity : AppCompatActivity() {

    var adapter:MyAdapter?=null

    private val items = listOf(
            "Mon 6/23 - Sunny - 31/17",
            "Tue 6/24 - Foggy - 21/8",
            "Wed 6/25 - Cloudy - 22/17",
            "Thurs 6/26 - Rainy - 18/11",
            "Fri 6/27 - Foggy - 21/10",
            "Sta 6/28 - TRAPPED IN WEATHERSTATION - 31/17",
            "Sun 6/29 - Sunny - 20/7"
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var forecastList :RecyclerView = findViewById(R.id.recyclerView)
        forecastList.layoutManager= LinearLayoutManager(this)
//        forecastList.adapter=ForecastListAdapter(items)

         adapter = MyAdapter(this,initData()!!)
        forecastList.adapter = adapter

    }

     fun  initData(): ArrayList<Img>?{

         var list = ArrayList<Img>()
         async() {
             val url = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"
             val forecastJsonStr = URL(url).readText()

             try {
                 var a = Gson().fromJson(forecastJsonStr, GankGril::class.java)
//                 list.clear()
                 var j=0
                    for(item in a.results){
                        var i = Img(item.who,item.url)
                        list.add(j,i)
                        j++
                    }

             } catch(e: Exception) {
                 Log.d("ATg","这里出现错误了"+e)
             }

             uiThread {
                 toast("数据加载完成")
                 adapter!!.notifyDataSetChanged()
             }
         }
         return list
     }

    fun Activity.toast(message:CharSequence, duration:Int=
    Toast.LENGTH_SHORT){
        Toast.makeText(this,message,duration).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
