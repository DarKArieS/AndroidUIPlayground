package com.app.aries.uiplayground.notification

import android.util.Log
import com.onesignal.OSNotification
import com.onesignal.OneSignal
import com.orhanobut.logger.Logger
import timber.log.Timber


class OneSignalNotification{

    fun setupNotification(){

    }
}

// Callback when receive a notification
class OneSignalNotificationReceivedHandler : OneSignal.NotificationReceivedHandler {
    override fun notificationReceived(notification: OSNotification) {
        val data = notification.payload.additionalData
        val notificationID = notification.payload.notificationID
        val title = notification.payload.title
        val body = notification.payload.body
        val smallIcon = notification.payload.smallIcon
        val largeIcon = notification.payload.largeIcon
        val bigPicture = notification.payload.bigPicture
        val smallIconAccentColor = notification.payload.smallIconAccentColor
        val sound = notification.payload.sound
        val ledColor = notification.payload.ledColor
        val lockScreenVisibility = notification.payload.lockScreenVisibility
        val groupKey = notification.payload.groupKey
        val groupMessage = notification.payload.groupMessage
        val fromProjectNumber = notification.payload.fromProjectNumber
        val rawPayload = notification.payload.rawPayload

        val customKey: String?

        Timber.tag("OneSignalExample").d("NotificationID received: $notificationID")

        if (data != null) {
            customKey = data.optString("customkey", null)
            if (customKey != null)
                Timber.tag("OneSignalExample").d("customkey set with value: $customKey")
        }

        Logger.t("OneSignalExample").json(notification.toJSONObject().toString())
    }
}


