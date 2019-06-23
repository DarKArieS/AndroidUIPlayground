package com.app.aries.uiplayground.service

import android.app.Service
import android.content.Intent
import android.os.*
import timber.log.Timber
import java.io.FileDescriptor

import com.orhanobut.logger.Logger

class MyService : Service() {
    var mThread : Thread? =null
    var mLooper : Looper? = null

    override fun onCreate() {
        Timber.tag("MyService").d("MyService created!")

        mThread = Thread{
            //Initialize the current thread as a looper.
            //Note: Only one Looper may be created per thread
            Looper.prepare()

            //Get the looper of this thread
            //Note: sThreadLocal.get() will return null unless you've called prepare().
            mLooper = Looper.myLooper()

            Looper.loop()
            Timber.tag("MyService").d("Looper Done")
        }
        mThread?.start()

        super.onCreate()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        Timber.tag("MyService").d("MyService destroy!")
        Handler(mLooper).removeCallbacks(runnable)
        mLooper?.quit()
        super.onDestroy()
    }

    var count = 0
    lateinit var runnable : Runnable

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.tag("MyService").d("MyService onStartCommand!")
        Timber.tag("MyService").d("flag: $flags , startId: $startId")

        val nowCount = count

        runnable = Runnable{
            Logger.t("MyService").d("MyService run on my looper by handler!")
            while(count < nowCount + 10){
                Timber.tag("MyService").d("handleActionFoo $count")
                count++
                Thread.sleep(1000)
            }
        }

        if(mLooper!=null) Handler(mLooper).post(runnable) // ToDo: why sometimes mLooper will be null ?

//        Thread{
//            while(count<100){
//                Timber.tag("MyService").d("handleActionFoo $count")
//                count++
//                Thread.sleep(1000)
//            }
//        }.start()

//        return super.onStartCommand(intent, flags, startId)
        return START_STICKY
//        return START_STICKY_COMPATIBILITY
//        return START_NOT_STICKY
//        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }
}
