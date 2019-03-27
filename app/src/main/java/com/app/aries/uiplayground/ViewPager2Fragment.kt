package com.app.aries.uiplayground


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import timber.log.Timber

class ViewPager2Fragment : Fragment() {
    init{
        Timber.tag("lifecycle").d("ViewPager2Fragment created!")
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
        return inflater.inflate(R.layout.fragment_view_pager2, container, false)
    }

    override fun onDestroy() {
        Timber.tag("lifecycle").d("ViewPager2Fragment onDestroy!")
        super.onDestroy()
    }


    companion object {
        @JvmStatic
        fun newInstance() = ViewPager2Fragment()
    }
}
