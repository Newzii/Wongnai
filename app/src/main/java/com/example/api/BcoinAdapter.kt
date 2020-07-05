package com.example.api

import android.app.Activity
import android.content.Intent
import android.content.Intent.getIntent
import android.text.Layout
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class BcoinAdapter(val context: Activity, val list:ArrayList<Bitcoin>) : RecyclerView.Adapter<ViewHolder>(){

    var intent: Intent? = getIntent(String())
    var id = ArrayList<Int>()
    val option = RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background).error(android.R.drawable.ic_dialog_alert)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val view = LayoutInflater.from(context).inflate(R.layout.list_bitcoin,parent,false)
            Log.d("int", view.toString())
            return ViewHolder(view)
    }


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (list[position].count % 5==0){
            holder.layNameDiff.visibility = View.VISIBLE
            holder.layNameDetail.visibility = View.GONE
         //   holder.layImg.gravity = Gravity.RIGHT
           // holder.layNameDiff.gravity = Gravity.RIGHT
            holder.layNameAll.gravity = Gravity.RIGHT
            holder.tvName.text = list[position].name
            holder.tvNameDiff.text = list[position].name
            holder.img.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            Glide.with(context).load(list[position].iconUrl).apply(option).into(holder.img)
            }else{
            holder.layNameDiff.visibility = View.GONE
            holder.layNameDetail.visibility = View.VISIBLE
            holder.tvName.text = list[position].name
            holder.tvNameDiff.text = list[position].name
            holder.tvDescription.text = list[position].description
            holder.img.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            Glide.with(context).load(list[position].iconUrl).apply(option).into(holder.img)
            }
//       holder.tvName.text = list[position].name
//        holder.tvDescription.text = list[position].description
//        holder.img.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        Glide.with(context).load(list[position].iconUrl).apply(option).into(holder.img)

//        SvgLoader.pluck()
//            .with(context)
//            .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
//            .load(list[position].iconUrl, holder.img)
        Log.d("showimage",list[position].iconUrl)
    }


}

class ViewHolder(itemView :View) : RecyclerView.ViewHolder(itemView){
    val tvName = itemView.findViewById<TextView>(R.id.name)
    val tvNameDiff = itemView.findViewById<TextView>(R.id.namediff)
    val tvDescription = itemView.findViewById<TextView>(R.id.description)
    val img = itemView.findViewById<ImageView>(R.id.iconUrl)
    val layNameDiff = itemView.findViewById<LinearLayout>(R.id.layoutFirst)
    val layImg= itemView.findViewById<LinearLayout>(R.id.layoutSecond)
    val layNameDetail = itemView.findViewById<LinearLayout>(R.id.layoutThird)
    val layNameAll = itemView.findViewById<LinearLayout>(R.id.layoutAll)
}