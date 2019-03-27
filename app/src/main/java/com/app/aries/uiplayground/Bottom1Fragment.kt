package com.app.aries.uiplayground

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.app.aries.uiplayground.bottom1fragment.*
import kotlinx.android.synthetic.main.fragment_bottom1.view.*
import timber.log.Timber

class Bottom1Fragment : Fragment() {
    lateinit var rootView :View
    private var mainActivity : MainActivity? = null

    init{
        Timber.tag("lifecycle").d("Bottom1Fragment created")
    }

    companion object {
        @JvmStatic
        fun newInstance() = Bottom1Fragment().apply {
        }
    }

    override fun onAttach(context: Context) {
        if (context is MainActivity) {
            mainActivity = (this.activity as MainActivity)
        }else mainActivity = null
        super.onAttach(context)
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
        setupToolBar()
        return rootView
    }

    override fun onDestroy() {
        Timber.tag("lifecycle").d("Bottom1Fragment onDestroy!")
        super.onDestroy()
    }

    private fun setupViewPager(){
        val childFragmentList = listOf("ViewPager1Fragment","ViewPager2Fragment")
//        rootView.viewPager.adapter = ViewPagerFragStateAdapter(this.childFragmentManager,childFragmentList)
        rootView.viewPager.adapter = ViewPagerFragStateAdapter(this.childFragmentManager,childFragmentList)
        rootView.tabLayout.setupWithViewPager(rootView.viewPager)

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



    private fun setupToolBar(){
        // setup tool bar without SupportActionBar
//        rootView.bottom1_toolbar.inflateMenu(R.menu.option_menu)
//        rootView.bottom1_toolbar.setOnMenuItemClickListener {
//            ... ...
//        }

        // setup tool bar with SupportActionBar
        // need to override fun onCreateOptionsMenu, onOptionsItemSelected
        if(mainActivity?.setToolBar(rootView.bottom1_toolbar)==true)
            this.setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
                (R.id.toolbar_action_search)->{
                    //click search
                    Timber.d("click toolbar search")
                }
            }
        return super.onOptionsItemSelected(item)
    }
}
