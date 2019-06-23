package com.app.aries.uiplayground.service

import android.app.IntentService
import android.content.Intent
import android.content.Context
import com.orhanobut.logger.Logger
import timber.log.Timber


/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class MyIntentService : IntentService("MyIntentService") {
    var count = 0
    /**
     *  The mission will run on the working thread.
     */
    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_FOO -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getIntExtra(EXTRA_PARAM2,10)
                handleActionFoo(param1, param2)
            }
            ACTION_BAZ -> {
                handleActionBaz()
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(param1: String, param2: Int) {
        Timber.tag("MyIntentService").d("handleActionFoo")
        Logger.t("MyIntentService").d("handleActionFoo")
        val nowCount = count
        while(count < nowCount + param2){
            Timber.tag("MyIntentService").d("handleActionFoo $count")
            count++
            Thread.sleep(1000)
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionBaz() {
        Timber.tag("MyIntentService").d("handleActionBaz")
        Logger.t("MyIntentService").d("handleActionBaz")
        val nowCount = count
        while(count < nowCount + 10){
            Timber.tag("MyIntentService").d("handleActionBaz $count")
            count++
            Thread.sleep(1000)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.tag("MyIntentService").d("onStartCommand")
        Timber.tag("MyIntentService").d("flag: $flags , startId: $startId")
        val tmp = super.onStartCommand(intent, flags, startId)
        Timber.tag("MyIntentService").d("start mode: $tmp")
        return tmp
    }

    override fun onDestroy() {
        Timber.tag("MyIntentService").d("onDestroy")
        super.onDestroy()
    }

    companion object {
        const val ACTION_FOO = "com.app.aries.uiplayground.service.action.FOO"
        const val ACTION_BAZ = "com.app.aries.uiplayground.service.action.BAZ"

        const val EXTRA_PARAM1 = "com.app.aries.uiplayground.service.extra.PARAM1"
        const val EXTRA_PARAM2 = "com.app.aries.uiplayground.service.extra.PARAM2"
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun param2(context: Context, param1: String, param2: String) {
            val intent = Intent(context, MyIntentService::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }

        /**
         * Starts this service to perform action Baz with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startActionBaz(context: Context, param1: String, param2: String) {
            val intent = Intent(context, MyIntentService::class.java).apply {
                action = ACTION_BAZ
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }
    }
}
