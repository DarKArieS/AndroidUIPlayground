package com.app.aries.uiplayground.bottom1fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.app.aries.uiplayground.ViewPager1Fragment
import com.app.aries.uiplayground.ViewPager2Fragment

class ViewPagerFragStateAdapter(val fm: FragmentManager, val fragmentTagList:List<String>)
    : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return fragmentTagList.size
    }

    override fun getItem(position: Int): Fragment {
        return when(fragmentTagList[position]){
            ("ViewPager1Fragment")-> ViewPager1Fragment.newInstance()
            ("ViewPager2Fragment")-> ViewPager2Fragment.newInstance()
            else->{throw Exception("ViewPagerFragStateAdapter getItem: No such kind of fragment!")}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTagList[position]
    }
}

class ViewPagerFragAdapter(val fm: FragmentManager, val fragmentTagList:List<String>)
    : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return fragmentTagList.size
    }

    override fun getItem(position: Int): Fragment {
        return when(fragmentTagList[position]){
            ("ViewPager1Fragment")-> ViewPager1Fragment.newInstance()
            ("ViewPager2Fragment")-> ViewPager2Fragment.newInstance()
            else->{throw Exception("ViewPagerFragStateAdapter getItem: No such kind of fragment!")}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTagList[position]
    }
}