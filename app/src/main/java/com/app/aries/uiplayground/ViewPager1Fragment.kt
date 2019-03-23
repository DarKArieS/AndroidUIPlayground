package com.app.aries.uiplayground


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aries.uiplayground.bottom1fragment.HomeRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_view_pager1.view.*

class ViewPager1Fragment : Fragment() {
    lateinit var rootView :View

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

        val fakeList = List<String>(20){ index ->"XDD"}
        setupAdapter(fakeList)

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

    private fun setupAdapter(itemList:List<String>) {
        rootView.homeRecyclerView.adapter = HomeRecyclerViewAdapter(this.context!!, itemList)
        rootView.homeRecyclerView.layoutManager = LinearLayoutManager(this.context!!)
    }
}
