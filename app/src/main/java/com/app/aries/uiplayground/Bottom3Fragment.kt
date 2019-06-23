package com.app.aries.uiplayground


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.app.aries.uiplayground.navigationmanager.FragmentFactory
import kotlinx.android.synthetic.main.fragment_bottom3.view.*
import timber.log.Timber

class Bottom3Fragment : Fragment() {
    private lateinit var rootView : View

    init{
        Timber.tag("lifecycle").d("Bottom3Fragment created")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_bottom3, container, false)

        rootView.navDrawer_left_bt3Test.setNavigationItemSelectedListener {item->

            when(item.itemId){
                (R.id.left_drawer_item_nav_bottom1)->{
                    // bala bala
                }
            }

            true
        }
        return rootView
    }

    override fun onDestroy() {
        Timber.tag("lifecycle").d("Bottom3Fragment onDestroy!")
        super.onDestroy()
    }

    companion object: FragmentFactory {
        @JvmStatic
        override fun newInstance() =
            Bottom3Fragment()
    }
}
