package com.app.aries.uiplayground.bottom1fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.aries.uiplayground.R
import com.app.aries.uiplayground.model.PostContent
import com.app.aries.uiplayground.model.bannerContent
import kotlinx.android.synthetic.main.item_pop_banner.view.*
import kotlinx.android.synthetic.main.item_post.view.*

class HomeRecyclerViewAdapter (val context: Context, val itemList: List<PostContent>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position].id==-1) -1
        else if (itemList[position].id==-2) -2
        else 1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return when{
            (p1==-1)->BannerContainerViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_pop_banner, p0, false))

            (p1==-2)->PostViewHolder(
                LayoutInflater.from(p0.context).inflate(R.layout.item_no_more, p0, false)
            )
            else->PostViewHolder(
                LayoutInflater.from(p0.context).inflate(R.layout.item_post, p0, false)
            )
        }
    }

    override fun getItemCount(): Int = this.itemList.count()

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        when{
            (itemList[p1].id>=0)->(p0 as PostViewHolder).bind(itemList[p1])
            (itemList[p1].id==-1)->(p0 as BannerContainerViewHolder).bind(itemList[p1])
        }
    }

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val postTitle = itemView.postTitle
        fun bind(item: PostContent) {
            postTitle.text = item.title
            adapterPosition
        }
    }

    inner class BannerContainerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init{
            setupAdapter()
        }

        private fun setupAdapter(){
            val List = listOf(bannerContent(),bannerContent(),bannerContent(),bannerContent(),bannerContent())

            itemView.popBannerRecycylerView.adapter = BannerAdapter(context, List)
            itemView.popBannerRecycylerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(itemView.popBannerRecycylerView)

            itemView.popBannerRecycylerView.onFlingListener
        }

        fun bind(item: PostContent) {

        }
    }
}