package com.app.aries.uiplayground.bottom1fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.aries.uiplayground.R
import com.app.aries.uiplayground.model.PostContent
import kotlinx.android.synthetic.main.item_post.view.*

class HomeRecyclerViewAdapter (val context: Context, val itemList: List<PostContent>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position].id==-1) -1
        else if (itemList[position].id==-2) -2
        else 1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val view = if(p1==-1)
            LayoutInflater.from(p0.context).inflate(R.layout.item_pop_banner, p0, false)
        else if (p1==-2)
            LayoutInflater.from(p0.context).inflate(R.layout.item_no_more, p0, false)
        else LayoutInflater.from(p0.context).inflate(R.layout.item_post, p0, false)

        return PostViewHolder(view)
    }

    override fun getItemCount(): Int = this.itemList.count()

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if(itemList[p1].id>=0) (p0 as PostViewHolder).bind(itemList[p1])
    }

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val postTitle = itemView.postTitle
        fun bind(item: PostContent) {
            postTitle.text = item.title
            adapterPosition
        }
    }
}