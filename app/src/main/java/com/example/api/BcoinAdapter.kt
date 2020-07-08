package com.example.api

import android.app.Activity
import android.content.Context.WINDOW_SERVICE
import android.net.Uri
import android.util.DisplayMetrics
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guardanis.imageloader.ImageRequest
import java.util.*
import kotlin.collections.ArrayList


class BcoinAdapter(val context: Activity, val list: ArrayList<Bitcoin>) :
    RecyclerView.Adapter<ViewHolder>() {
    var widthInDP: Int = 0
    var bitList: ArrayList<Bitcoin>? = null

    init {
        bitList = ArrayList()
        bitList!!.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Find width screen and calculate size for image
        val dm = DisplayMetrics()
        val windowManager =
            context.getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(dm)
        widthInDP = Math.round(dm.widthPixels / dm.density) / 3

        val view = LayoutInflater.from(context).inflate(R.layout.list_bitcoin, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Display different view at position 5, 10, 15, 20, ...
        if (list[position].count % 5 == 0) {
            holder.layNameDiff.visibility = View.VISIBLE
            holder.layNameDetail.visibility = View.GONE
            holder.layNameAll.gravity = Gravity.RIGHT
            holder.tvNameDiff.text = list[position].name
            holder.img.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            ImageRequest.create(holder.img)
                .setTargetUrl(Uri.parse(list[position].iconUrl).toString())
                .setRequiredImageWidth(widthInDP)
                .execute()
        } else {
            holder.layNameDiff.visibility = View.GONE
            holder.layNameDetail.visibility = View.VISIBLE
            holder.tvName.text = list[position].name
            holder.tvDescription.text = list[position].description
            holder.img.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            ImageRequest.create(holder.img)
                .setTargetUrl(Uri.parse(list[position].iconUrl).toString())
                .setRequiredImageWidth(widthInDP)
                .execute()
        }
    }

    //Search filter
    open fun filter(charText: String): Unit {

        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())

        list.clear()
        if (charText.isEmpty()) {
            if (bitList != null) {
                list.addAll(bitList!!)
            }
        } else {
            if (bitList != null) {
                for (data in bitList!!) {
                    if (data.name!!.toLowerCase(Locale.getDefault())
                            .contains(charText)
                    ) {
                        list.add(data)
                    } else if (data.symbol!!.toLowerCase(Locale.getDefault())
                            .contains(charText)
                    ) {
                        list.add(data)
                    } else if (data.slug!!.toLowerCase(Locale.getDefault())
                            .contains(charText)
                    ) {
                        list.add(data)
                    } else if (data.id!!.toString().toLowerCase(Locale.getDefault())
                            .contains(charText)
                    ) {
                        list.add(data)
                    } else if (data.uuid!!.toLowerCase(Locale.getDefault())
                            .contains(charText)
                    ) {
                        list.add(data)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.findViewById<TextView>(R.id.name)
    val tvNameDiff = itemView.findViewById<TextView>(R.id.namediff)
    val tvDescription = itemView.findViewById<TextView>(R.id.description)
    val img = itemView.findViewById<ImageView>(R.id.iconUrl)
    val layNameDiff = itemView.findViewById<LinearLayout>(R.id.layoutFirst)
    val layNameDetail = itemView.findViewById<LinearLayout>(R.id.layoutThird)
    val layNameAll = itemView.findViewById<LinearLayout>(R.id.layoutAll)
}

