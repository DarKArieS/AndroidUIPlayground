package com.app.aries.uiplayground


import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import timber.log.Timber

class CustomApplication : Application() {
    companion object {
        private lateinit var myApplication : CustomApplication
        fun getInstance() = myApplication
    }

    override fun onCreate(){
        myApplication = this

        if (BuildConfig.DEBUG) {
            // problem:  can't not track the method in the logger logs, always be "Timber$Tree.prepareLog  (Timber.java:532)"
            // solution: Set methodOffset to 5 in order to hide internal method calls
//            Timber.plant(object: Timber.DebugTree(){
//                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//                    Logger.log(priority,tag,message,t)
//                }
//            })

            Timber.plant(Timber.DebugTree())
            Logger.addLogAdapter(AndroidLogAdapter())

//            val format = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(0)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                //.logStrategy() // (Optional) Changes the log strategy to print out. Default LogCat
//                .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .build()
//            Logger.addLogAdapter(AndroidLogAdapter(format))
            //Logger.clearLogAdapters()
        }

        super.onCreate()
    }

}