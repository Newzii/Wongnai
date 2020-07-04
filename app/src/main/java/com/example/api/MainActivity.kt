package com.example.api

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private val list =ArrayList<Bitcoin>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callVolley()
    }
    private fun callVolley(){
        val url = "https://api.coinranking.com/v1/public/coins"
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
//                Log.d("test", response.toString())
                Log.d("test", response.getJSONObject("data").getJSONArray("coins").toString())
                try {
                    val jsonArray = response.getJSONObject("data").getJSONArray("coins")
                    for (i in 0 until jsonArray.length()){
                        val hit = jsonArray.getJSONObject(i)
                        Log.d("test", hit.getString("name"))

                            val id =hit.getString("id")
                            val name = hit.getString("name")
                            val description = hit.getString("description")
                            val iconUrl = hit.getString("iconUrl")
                        list!!.add(Bitcoin(name, description, iconUrl))

                    }
                    recycler_view.setHasFixedSize(true)
                    recycler_view.layoutManager = LinearLayoutManager(this)
                    val adapter = BcoinAdapter(this,list)
                    recycler_view.adapter = adapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> error.printStackTrace() })
        val queue = Volley.newRequestQueue(this)
        queue!!.add(request)

    }



}
