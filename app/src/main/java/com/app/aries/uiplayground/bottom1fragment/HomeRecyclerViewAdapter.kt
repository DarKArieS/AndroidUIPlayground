package com.app.aries.uiplayground.bottom1fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.aries.uiplayground.R

class HomeRecyclerViewAdapter (val context: Context, val itemList: List<String>):
    RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position==0) 0
        else 1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = if(p1==0)
            LayoutInflater.from(p0.context).inflate(R.layout.item_pop_banner, p0, false)
        else LayoutInflater.from(p0.context).inflate(R.layout.item_post, p0, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = this.itemList.count()

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(itemList[p1])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) {

        }
    }
}