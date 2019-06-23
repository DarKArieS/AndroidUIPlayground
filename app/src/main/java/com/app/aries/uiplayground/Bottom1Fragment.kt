package com.app.aries.uiplayground

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import com.app.aries.uiplayground.bottom1fragment.*
import com.app.aries.uiplayground.navigationmanager.FragmentFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_bottom1.view.*
import timber.log.Timber

class Bottom1Fragment : Fragment(), SearchBottomSheetListDialogFragment.Listener {
    override fun onTestBottomSheetClicked(position: Int) {
        Timber.tag("TestBottomSheet").d("onTestBottomSheetClicked")
    }

    lateinit var rootView :View
    private var mainActivity : MainActivity? = null

    init{
        Timber.tag("lifecycle").d("Bottom1Fragment created")
    }

    companion object:FragmentFactory {
        @JvmStatic
        override fun newInstance() = Bottom1Fragment().apply {
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

    @UiThread
    private fun setupViewPager(){
        val childFragmentList = listOf(
            "ViewPager1Fragment",
            "ViewPager2Fragment",
            "ViewPager2Fragment",
            "ViewPager2Fragment",
            "ViewPager2Fragment",
            "ViewPager2Fragment",
            "ViewPager2Fragment")
        rootView.viewPager.adapter = ViewPagerFragAdapter(this.childFragmentManager,childFragmentList)
//        rootView.viewPager.adapter = ViewPagerFragStateAdapter(this.childFragmentManager,childFragmentList)
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
        if(mainActivity?.setToolBarFromFragment(rootView.bottom1_toolbar)==true)
            this.setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            (R.id.toolbar_action_setting)->{
                //click setting
                Timber.d("click toolbar setting")

                //open setting frag

            }
            (R.id.toolbar_action_search)->{
                //click search
                Timber.d("click toolbar search")
                SearchBottomSheetListDialogFragment.newInstance().show(this.childFragmentManager, "SearchBottomSheetDialog")
            }
            (R.id.toolbar_action_edit)->{
                mainActivity?.hideBottomNavigation{
                    val bottomSheetBehavior = BottomSheetBehavior.from(rootView.bottomSheet1)



                    bottomSheetBehavior.getScrimColor(rootView.bottom1Coordinatorlayout,rootView.bottomSheet1)

                    bottomSheetBehavior.getScrimOpacity(rootView.bottom1Coordinatorlayout,rootView.bottomSheet1)

                    val a = bottomSheetBehavior.state
                    Timber.tag("bottomSheet").d("original state = $a")

                    bottomSheetBehavior.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
                        override fun onSlide(bottomSheet: View, slideOffset: Float) {

                        }

                        override fun onStateChanged(bottomSheet: View, newState: Int) {
                            Timber.tag("bottomSheet").d("state = $newState")
                            if(bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN)
                                mainActivity?.showBottomNavigation()
                        }
                    })

                    bottomSheetBehavior.setPeekHeight(400,true) //init: px
                    if(bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED)
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

                    Timber.tag("bottomSheet").d("after opening state = $a")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
