package com.example.api

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    val list: MutableList<Bitcoin> = ArrayList()
    lateinit var adapter: BcoinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //** Set the colors of the Pull To Refresh View
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        itemsswipetorefresh.setOnRefreshListener {
            callVolley()
            itemsswipetorefresh.isRefreshing = false
        }

        search()
        callVolley()
    }

    private fun callVolley() {
        val url = "https://api.coinranking.com/v1/public/coins"
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    list.clear()
                    val jsonArray = response.getJSONObject("data").getJSONArray("coins")
                    for (i in 0 until jsonArray.length()) {
                        val hit = jsonArray.getJSONObject(i)
                        val count = i + 1
                        val id = hit.getInt("id")
                        val uuid = hit.getString("uuid")
                        val slug = hit.getString("slug")
                        val symbol = hit.getString("symbol")
                        val name = hit.getString("name")
                        val description = hit.getString("description")
                        val iconUrl = hit.getString("iconUrl")
                        list!!.add(Bitcoin(count, id, uuid, slug, symbol, name, description, iconUrl))
                    }

                    recycler_view.setHasFixedSize(true)
                    recycler_view.layoutManager = LinearLayoutManager(this)
                    adapter = BcoinAdapter(this, list as ArrayList<Bitcoin>)
                    recycler_view.adapter = adapter

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> error.printStackTrace() })
        val queue = Volley.newRequestQueue(this)
        queue!!.add(request)
    }

    fun search() {
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                val text: String =
                    search.getText().toString().toLowerCase(Locale.getDefault())
                adapter.filter(text)
            }

            override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

        })
    }

}


