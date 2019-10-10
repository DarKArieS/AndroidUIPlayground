package com.app.aries.uiplayground.navigationmanager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class NavigationManager(
    private val fragmentManager: FragmentManager,
    private val fragmentContainer:Int,
    var currentFragmentTag:String
){
    init{
        navigateTo(currentFragmentTag)
    }

    private enum class FragmentStatus{
        SAME,EXIST,ABSENCE;

        var existFragment: Fragment? = null
    }

    fun navigateTo(tag:String){
        // ToDoDone: study: use show/hide or attach/detach
        // use attach/detach is memory saved but laggy ...
        // attach/detach will remove the fragment from back stack !?
        val status = checkFragmentStatus(tag)
        if(status == FragmentStatus.SAME) return
        val currentFrag = fragmentManager.findFragmentByTag(currentFragmentTag)
        val transaction = fragmentManager.beginTransaction()
        if(currentFrag!=null) transaction.hide(currentFrag)
        if(status == FragmentStatus.ABSENCE){
            status.existFragment = createFragment(tag)
            transaction.add(fragmentContainer,status.existFragment!!,tag)
        }else transaction.show(status.existFragment!!)
        transaction.commit()

        currentFragmentTag = tag
    }

    fun forwardNavigation(tag:String){

    }

    private fun checkFragmentStatus(tag:String):FragmentStatus{
        val foundFrag = fragmentManager.findFragmentByTag(tag)
        val status = when{
            (foundFrag == null)->FragmentStatus.ABSENCE
            foundFrag.isVisible->FragmentStatus.SAME
            else->FragmentStatus.EXIST
        }
        status.existFragment = foundFrag
        return status
    }

    abstract fun createFragment(tag:String):Fragment?

}