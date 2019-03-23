package com.app.aries.uiplayground


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aries.uiplayground.bottom1fragment.*
import kotlinx.android.synthetic.main.fragment_bottom1.view.*

class Bottom1Fragment : Fragment() {
    lateinit var rootView :View

    init{
        println("Bottom1Fragment created")
    }

    companion object {
        @JvmStatic
        fun newInstance() = Bottom1Fragment()
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
        rootView = inflater.inflate(R.layout.fragment_bottom1, container, false)

        setupViewPager()
        rootView.tabLayout.setupWithViewPager(rootView.viewPager)

        return rootView
    }

    override fun onDestroy() {
        println("Bottom1Fragment onDestroy!")
        super.onDestroy()
    }

    private fun setupViewPager(){
        val childFragmentList = listOf("ViewPager1Fragment","ViewPager2Fragment")
//        rootView.viewPager.adapter = ViewPagerFragStateAdapter(this.childFragmentManager,childFragmentList)
        rootView.viewPager.adapter = ViewPagerFragStateAdapter(this.childFragmentManager,childFragmentList)

//        rootView.viewPager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
//            override fun onPageScrollStateChanged(state: Int) {
//
//            }
//
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//
//            }
//
//            override fun onPageSelected(position: Int) {
//
//            }
//        })
    }



}
