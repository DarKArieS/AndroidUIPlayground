package com.app.aries.uiplayground

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import com.app.aries.uiplayground.navigationmanager.NavigationManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import android.os.Handler
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.transaction

class MainActivity : BaseActivity() {
    private lateinit var navigationManager : NavigationManager

    init{
        Timber.tag("lifecycle").d("MainActivity created")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigationManager()
        setupBottomNavigation()
        setupLeftNavigationDrawer()
//        threadTest()

        val mNotificationManager = NotificationManagerCompat.from(this)
        mNotificationManager.cancel(1)
    }

    override fun onPause() {
        this.getSharedPreferences("UI_SF", Context.MODE_PRIVATE).edit().putString("LAST_FRAG",navigationManager.currentFragmentTag).apply()
        super.onPause()
    }

    override fun onDestroy() {
        Timber.tag("lifecycle").d("MainActivity onDestroy")
        super.onDestroy()
    }

    override fun onTrimMemory(level: Int) {
        Timber.tag("lifecycle").d("onTrimMemory $level")
        super.onTrimMemory(level)
    }

    private fun setupNavigationManager(){
        navigationManager = object: NavigationManager(
            this.supportFragmentManager,
            R.id.mainFragmentContainer,
            this.getSharedPreferences("UI_SF", Context.MODE_PRIVATE)
                .getString("LAST_FRAG","bottom1Fragment") ?: "bottom1Fragment"
        ){
            override fun createFragment(tag: String): Fragment? {
                // should be a factory design pattern
                    return when(tag){
                        "bottom1Fragment"-> Bottom1Fragment.newInstance()
//                        "bottom1Fragment"-> ViewPager1Fragment.newInstance()
                        "bottom2Fragment"-> Bottom2Fragment.newInstance()
                        "bottom3Fragment"-> Bottom3Fragment.newInstance()
                        else->null
                    }
            }
        }
        this.supportFragmentManager.transaction {  }
    }

    private var blockBottomNavigationSelectedListener = false
    private fun setupBottomNavigation(){
        setBottomNavigationSelectedItemByFragmentTag(navigationManager.currentFragmentTag)

        bottomNavigation.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                val tag = when (item.itemId) {
                    R.id.navigation_home -> {
                        "bottom1Fragment"
                    }
                    R.id.navigation_dashboard -> {
                        "bottom2Fragment"
                    }
                    R.id.navigation_notifications -> {
                        "bottom3Fragment"
                    }

                    else->return@OnNavigationItemSelectedListener false
                }
                if(!blockBottomNavigationSelectedListener)
                    navigationManager.navigateTo(tag)

                setNavigationDrawerSelectedItemByFragmentTag(tag)
                return@OnNavigationItemSelectedListener true
            })
    }

    private fun setupLeftNavigationDrawer(){
        setNavigationDrawerSelectedItemByFragmentTag(navigationManager.currentFragmentTag)
        navDrawer_left.setNavigationItemSelectedListener { item->
            drawerLayout.closeDrawers()
            val tag = when(item.itemId){
                (R.id.left_drawer_item_nav_bottom1)->{
                    "bottom1Fragment"
                }
                (R.id.left_drawer_item_nav_bottom2)->{
                    "bottom2Fragment"
                }
                (R.id.left_drawer_item_nav_bottom3)->{
                    "bottom3Fragment"
                }
                else->return@setNavigationItemSelectedListener false
            }
            navigationManager.navigateTo(tag)
            setBottomNavigationSelectedItemByFragmentTag(tag)
            return@setNavigationItemSelectedListener true
        }
    }

    private fun setBottomNavigationSelectedItemByFragmentTag(tag:String){
        blockBottomNavigationSelectedListener = true
        bottomNavigation.selectedItemId = when(tag){
            "bottom1Fragment"->R.id.navigation_home
            "bottom2Fragment"->R.id.navigation_dashboard
            "bottom3Fragment"->R.id.navigation_notifications
            else->R.id.navigation_home
        }
        blockBottomNavigationSelectedListener = false
    }

    private fun setNavigationDrawerSelectedItemByFragmentTag(tag:String){
        when(tag){
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

    fun setToolBarFromFragment(toolbar: Toolbar):Boolean{
        this.setSupportActionBar(toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setHomeButtonEnabled(true)

        val drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.app_name, R.string.app_name)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        return (this.supportActionBar!=null)
    }

    private fun setBottomNavigationAnimation(callback: ()->Unit = {}){
        val hideBottomNavigationAnimator = ObjectAnimator.ofFloat(bottomNavigation,"translationY",0f,bottomNavigation.height * 1f).setDuration(100)
        hideBottomNavigationAnimator.interpolator = DecelerateInterpolator()
        hideBottomNavigationAnimator.addListener (onEnd={callback.invoke()} )

        val showBottomNavigationAnimator = ObjectAnimator.ofFloat(bottomNavigation,"translationY",bottomNavigation.height * 1f, 0f).setDuration(100)
        showBottomNavigationAnimator.interpolator = DecelerateInterpolator()
        showBottomNavigationAnimator.addListener (onEnd={callback.invoke()} )

        constraintLayout.layoutTransition = LayoutTransition().apply{
            this.setAnimator(LayoutTransition.APPEARING,showBottomNavigationAnimator)
            this.setAnimator(LayoutTransition.DISAPPEARING,hideBottomNavigationAnimator)
        }
    }

    fun hideBottomNavigation(callback: ()->Unit){
        setBottomNavigationAnimation(callback)
        bottomNavigation.visibility = View.GONE
    }

    fun showBottomNavigation(){
        setBottomNavigationAnimation()
        bottomNavigation.visibility = View.VISIBLE
    }

    private fun threadTest(){
        Thread{
            var time = 0
            while(true){
                Timber.tag("ThreadTest").d(" I'm still alive! $time")
                time += 1
                Thread.sleep(1000)
            }
        }.start()
    }

    private fun handlerTest(){
        val a = Handler()
        var time = 0
        lateinit var r: Runnable

        r = Runnable{
                Timber.tag("HandlerTest").d(" I'm still alive! $time")
                time += 1
                a.postDelayed(r,1000)
        }
        r.run()
    }

    private fun serviceTest(){

    }
}
