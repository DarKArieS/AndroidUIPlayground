package com.app.aries.uiplayground.bottom1fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.app.aries.uiplayground.ViewPager1Fragment
import com.app.aries.uiplayground.ViewPager2Fragment

class ViewPagerPageTitle{
    companion object {
        val pageTitle = listOf(
            "主頁",
            "測試頁"
        )
    }
}

class ViewPagerFragStateAdapter(fm: FragmentManager, val fragmentTagList:List<String>)
    : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return fragmentTagList.size
    }

    override fun getItem(position: Int): Fragment {
        return when(fragmentTagList[position]){
            ("ViewPager1Fragment")-> ViewPager1Fragment.newInstance()
            else->ViewPager2Fragment.newInstance(position)
//            ("ViewPager2Fragment")-> ViewPager2Fragment.newInstance()
//            else->{throw Exception("ViewPagerFragStateAdapter getItem: No such kind of fragment!")}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0->"主頁"
            else->"測試頁 $position"
        }
    }
}

class ViewPagerFragAdapter(fm: FragmentManager, val fragmentTagList:List<String>)
    : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return fragmentTagList.size
    }

    override fun getItem(position: Int): Fragment {
        return when(fragmentTagList[position]){
            ("ViewPager1Fragment")-> ViewPager1Fragment.newInstance()
            else->ViewPager2Fragment.newInstance(position)
//            ("ViewPager2Fragment")-> ViewPager2Fragment.newInstance()
//            else->{throw Exception("ViewPagerFragStateAdapter getItem: No such kind of fragment!")}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0->"主頁"
            else->"測試頁 $position"
        }
    }
}