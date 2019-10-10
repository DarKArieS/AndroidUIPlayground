package com.app.aries.uiplayground


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_view_pager2.view.*
import timber.log.Timber

class ViewPager2Fragment : Fragment() {
    private var mNumber = 2
    lateinit var rootView: View

    init {
        Timber.tag("lifecycle").d("ViewPagers NO.$mNumber Fragment created!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mNumber = it.getInt("NO", 2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_pager2, container, false)
        rootView.viewPagersNumber.text = "viewPagers No. $mNumber"
        return rootView
    }

    override fun onDestroy() {
        Timber.tag("lifecycle").d("ViewPager NO.$mNumber Fragment onDestroy!")
        super.onDestroy()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }


    companion object {
        @JvmStatic
        fun newInstance() = ViewPager2Fragment()

        fun newInstance(number: Int) = ViewPager2Fragment().apply {
            arguments = Bundle().apply {
                putInt("NO", number)
                Timber.tag("lifecycle").d("Factory: ViewPagers NO.$number Fragment created!")
            }
        }
    }
}
