package com.app.aries.uiplayground

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

private enum class FragmentStatus{
    SAME,EXIST,ABSENCE;

    var existFragment:Fragment? = null
}

class MainActivity : AppCompatActivity() {
    private var currentFragmentTag = "bottom1Fragment"
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                navigateTo("bottom1Fragment")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                navigateTo("bottom2Fragment")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                navigateTo("bottom3Fragment")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        currentFragmentTag = this.getSharedPreferences("UI_SF", Context.MODE_PRIVATE).getString("LAST_FRAG","bottom1Fragment") ?: "bottom1Fragment"
        navigation.selectedItemId = getBottomNavigationItemID(currentFragmentTag)

        initLeftNavigationDrawer()
    }

    override fun onPause() {
        this.getSharedPreferences("UI_SF", Context.MODE_PRIVATE).edit().putString("LAST_FRAG",currentFragmentTag).apply()
        super.onPause()
    }

    private fun checkFragmentStatus(tag:String):FragmentStatus{
        val foundFrag = this.supportFragmentManager.findFragmentByTag(tag)
        val status = when{
            (foundFrag == null)->FragmentStatus.ABSENCE
            foundFrag.isVisible->FragmentStatus.SAME
            else->FragmentStatus.EXIST
        }
        status.existFragment = foundFrag
        return status
    }

    private fun navigateTo(tag:String){
        // ToDoDone: study: use show/hide or attach/detach
        // use attach/detach is memory saved but laggy ...
        // attach/detach will remove the fragment from back stack !?
        val status = checkFragmentStatus(tag)
        if(status == FragmentStatus.SAME) return
        val currentFrag = this.supportFragmentManager.findFragmentByTag(currentFragmentTag)
        val transaction = this.supportFragmentManager.beginTransaction()
        if(currentFrag!=null) transaction.hide(currentFrag)
        if(status == FragmentStatus.ABSENCE){
            status.existFragment = createFragment(tag)
            transaction.add(R.id.mainFragmentContainer,status.existFragment!!,tag)
        }else transaction.show(status.existFragment!!)
        transaction.commit()

        currentFragmentTag = tag
        setLeftNavDrawerItemCheck()
    }

    private fun getBottomNavigationItemID(tag:String):Int{
        return when(tag){
            "bottom1Fragment"->R.id.navigation_home
            "bottom2Fragment"->R.id.navigation_dashboard
            "bottom3Fragment"->R.id.navigation_notifications
            else->R.id.navigation_home
        }
    }

    private fun createFragment(tag:String):Fragment?{
        // should be a factory design pattern
        return when(tag){
            "bottom1Fragment"->Bottom1Fragment.newInstance()
            "bottom2Fragment"->Bottom2Fragment.newInstance()
            "bottom3Fragment"->Bottom3Fragment.newInstance()
            else->null
        }
    }

    fun setToolBar(toolbar: Toolbar):Boolean{
        this.setSupportActionBar(toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setHomeButtonEnabled(true)

        val drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.app_name, R.string.app_name)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        return (this.supportActionBar!=null)
    }

    private fun initLeftNavigationDrawer(){
        setLeftNavDrawerItemCheck()
        navDrawer_left.setNavigationItemSelectedListener { item->
            drawerLayout.closeDrawers()
            when(item.itemId){
                (R.id.left_drawer_item_nav_bottom1)->{
                    navigation.selectedItemId = getBottomNavigationItemID("bottom1Fragment")
                    return@setNavigationItemSelectedListener true
                }
                (R.id.left_drawer_item_nav_bottom2)->{
                    navigation.selectedItemId = getBottomNavigationItemID("bottom2Fragment")
                    return@setNavigationItemSelectedListener true
                }
                (R.id.left_drawer_item_nav_bottom3)->{
                    navigation.selectedItemId = getBottomNavigationItemID("bottom3Fragment")
                    return@setNavigationItemSelectedListener true
                }
                else->return@setNavigationItemSelectedListener false
            }
        }
    }

    private fun setLeftNavDrawerItemCheck(){
        navDrawer_left.menu.findItem(R.id.left_drawer_item_nav_bottom1).isChecked = false
        navDrawer_left.menu.findItem(R.id.left_drawer_item_nav_bottom2).isChecked = false
        navDrawer_left.menu.findItem(R.id.left_drawer_item_nav_bottom3).isChecked = false

        when (currentFragmentTag){
            "bottom1Fragment"->{
                navDrawer_left.menu.findItem(R.id.left_drawer_item_nav_bottom1).isChecked = true
            }
            "bottom2Fragment"->{
                navDrawer_left.menu.findItem(R.id.left_drawer_item_nav_bottom2).isChecked = true
            }
            "bottom3Fragment"->{
                navDrawer_left.menu.findItem(R.id.left_drawer_item_nav_bottom3).isChecked = true
            }
        }
    }
}
