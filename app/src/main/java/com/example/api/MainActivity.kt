package com.example.api

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private val list =ArrayList<Bitcoin>()
    val id = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callVolley()
        //** Set the colors of the Pull To Refresh View
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)


        itemsswipetorefresh.setOnRefreshListener {
//            itemsCells.clear()
//            refreshTimes =+ refreshTimes + 5
//            setItemsData(refreshTimes)
//            adapter = Items_RVAdapter(itemsCells)
//            itemsrv.adapter = adapter
            callVolley()
            itemsswipetorefresh.isRefreshing = false
        }
    }

    private fun callVolley(){
        val url = "https://api.coinranking.com/v1/public/coins"
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
//                Log.d("test", response.toString())
//                Log.d("test", response.getJSONObject("data").getJSONArray("coins").toString())
                try {
                    list.clear()
                    val jsonArray = response.getJSONObject("data").getJSONArray("coins")
                    for (i in 0 until jsonArray.length()){
                        val hit = jsonArray.getJSONObject(i)
//                      Log.d("checkid", hit.getString("id"))
                            val count = i+1;
                            val name = hit.getString("name")
                            val description = hit.getString("description")
                            val iconUrl = hit.getString("iconUrl")
                        list!!.add(Bitcoin(count,name, description, iconUrl))
                        id.add(i)
                        Log.d("cI",count.toString())
                    }
//                    Intent(this, BcoinAdapter::class.java)
//                    var bundle = Bundle()
//                    bundle.putSerializable("checkId",id)
//                     intent.putExtra("id",bundle)
//                    startActivity(intent)

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


