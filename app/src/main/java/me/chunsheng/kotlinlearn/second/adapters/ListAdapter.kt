package me.chunsheng.kotlinlearn.second.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*
import me.chunsheng.kotlinlearn.R
import me.chunsheng.kotlinlearn.second.model.DailyData

/**
 * Copyright © 2016 edaixi. All Rights Reserved.
 * Author: wei_spring
 * Date: 2017/8/23
 * Email:weichsh@edaixi.com
 * Function:
 */
class ListAdapter(val dailyList: List<DailyData>, val itemClick: (DailyData) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(dailyList[position])
    }


    override fun getItemCount(): Int {
        return dailyList.size
    }


    class ViewHolder(view: View, val itemClick: (DailyData) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindForecast(forecast: DailyData) {
            itemView.caption.text = forecast.caption
            itemView.note.text = forecast.note
            itemView.love.text = "😎😍" + forecast.love
            itemView.dateline.text = forecast.dateline
            Picasso.with(itemView.context).load(forecast.picture2).into(itemView.icon)
            itemView.setOnClickListener { itemClick(forecast) }
        }

    }

}