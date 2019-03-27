package com.app.aries.uiplayground.bottom1fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.aries.uiplayground.R
import com.app.aries.uiplayground.model.bannerContent

class BannerAdapter (val context: Context, val itemList: List<bannerContent>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_pop_banner_item, p0, false)

        return BannerViewHolder(view)
    }

    override fun getItemCount(): Int = this.itemList.count()

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if(itemList[p1].id>=0) (p0 as BannerViewHolder).bind(itemList[p1])
    }

    inner class BannerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: bannerContent) {

        }
    }
}