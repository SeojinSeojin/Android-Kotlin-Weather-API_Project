package com.example.weatherapipractice

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val items: ArrayList<AirPollutionInfo>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.card_air_pollution, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
       val item = items[position]
        holder.apply { bind(item) }
    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        private var view : View = v
        fun bind(item : AirPollutionInfo) {
            view.findViewById<TextView>(R.id.name).text = item.name
            view.findViewById<TextView>(R.id.degree).text = item.degree.toString()
            val targetDrawable =
                when {
                    item.bad == 0.0 -> getDrawable(view.context, R.drawable.card_grey)
                    item.degree!! < item.good -> getDrawable(view.context, R.drawable.card_green)
                    item.degree > item.bad -> getDrawable(view.context, R.drawable.card_red)
                    else -> getDrawable(view.context, R.drawable.card_blue)
                }
            view.findViewById<ImageView>(R.id.background).setImageDrawable(targetDrawable)
        }
    }
}