package com.example.api

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrosid.svgloader.SvgLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class BcoinAdapter(val context: Activity, val list:ArrayList<Bitcoin>) : RecyclerView.Adapter<ViewHolder>(){

    val option = RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background).error(android.R.drawable.ic_dialog_alert)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_bitcoin,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = list[position].name
        holder.tvDescription.text = list[position].description
        holder.img.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Glide.with(context).load(list[position].iconUrl).apply(option).into(holder.img)
//        SvgLoader.pluck()
//            .with(context)
//            .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
//            .load(list[position].iconUrl, holder.img)
//        Glide.with(context).load( File(list[position].iconUrl)).(option).into(holder.img)
        Log.d("showimage",list[position].iconUrl)
    }
}
class ViewHolder(itemView :View) : RecyclerView.ViewHolder(itemView){
    val tvName = itemView.findViewById<TextView>(R.id.name)
    val tvDescription = itemView.findViewById<TextView>(R.id.description)
    val img = itemView.findViewById<ImageView>(R.id.iconUrl)

}