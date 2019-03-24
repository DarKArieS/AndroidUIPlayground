package com.app.aries.uiplayground


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.aries.uiplayground.bottom1fragment.HomeRecyclerViewAdapter
import com.app.aries.uiplayground.model.PostContent
import com.app.aries.uiplayground.model.ViewPager1Model
import kotlinx.android.synthetic.main.fragment_view_pager1.view.*

class ViewPager1Fragment : Fragment() {
    lateinit var rootView :View
    private val viewPager1Model = ViewPager1Model()

    init{
        println("ViewPager1Fragment created!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_pager1, container, false)

        viewPager1Model.updateFakePostContentList(10)
        setupAdapter(viewPager1Model.postContentList)

        return rootView
    }

    override fun onDestroy() {
        println("ViewPager1Fragment onDestroy!")
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ViewPager1Fragment()
    }

    private fun setupAdapter(itemList:List<PostContent>) {
        rootView.homeRecyclerView.adapter = HomeRecyclerViewAdapter(this.context!!, itemList)
        rootView.homeRecyclerView.layoutManager = LinearLayoutManager(this.context!!)
        rootView.homeRecyclerView.addOnScrollListener(
            object: RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    println("newState: $newState")
                    super.onScrollStateChanged(recyclerView, newState)
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val childCount  = rootView.homeRecyclerView.childCount
                    val lastVisibItem = rootView.homeRecyclerView.getChildAt(childCount-1)
                    val position = rootView.homeRecyclerView.getChildAdapterPosition(lastVisibItem)
                    println("onScrolled: dx: $dx dy: $dy position: $position" )
                    if(position == rootView.homeRecyclerView.adapter!!.itemCount - 1){
                        viewPager1Model.updateFakePostContentList(10)
                        rootView.homeRecyclerView.adapter!!.notifyDataSetChanged()
                    }

                    super.onScrolled(recyclerView, dx, dy)
                }
            }
        )
    }
}
