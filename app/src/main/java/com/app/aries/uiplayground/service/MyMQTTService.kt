package com.app.aries.uiplayground.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import timber.log.Timber

class MyMQTTService : Service() {
    private val myTopic = "test"

    private val client = MqttAndroidClient(this, "tcp://192.168.56.216:5487" ,"ohya")

    //used to set Timeout, user name, pwd, ... , ...
    private val connectOpt = MqttConnectOptions()

    inner class ServiceBinder: Binder(){
        fun getService() = this@MyMQTTService
    }

    private val binder = ServiceBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initService()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onDestroy() {
        if(client.isConnected) client.disconnect()
        super.onDestroy()
    }

    private fun initService(){
        client.setCallback(mqttCallback)

        if(!client.isConnected){
            try {
                client.connect(connectOpt, null, mIMqttActionListener)
            } catch (e:MqttException) {
                e.printStackTrace()
            }
        }
    }

    private val mqttCallback = object: MqttCallback{
        override fun messageArrived(topic: String?, message: MqttMessage?) {
            Timber.tag("MQTT").d("Receive something")

            val receivedString = String(message!!.payload)

            Timber.tag("MQTT").d("$topic : $receivedString")
        }

        override fun connectionLost(cause: Throwable?) {
            Timber.tag("MQTT").d("connectionLost:  $cause")
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            val content = String(token!!.message.payload)
            Timber.tag("MQTT").d("deliveryComplete:  $content")
        }


    }

    private val mIMqttActionListener = object:IMqttActionListener{
        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Timber.tag("MQTT").d("connect onFailure!")
        }

        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Timber.tag("MQTT").d("connect onSuccess!")
            client.subscribe(myTopic,1)
        }
    }

    // public to MQTT server from client
    fun publish(msg:String){
        try {
            client.publish(myTopic, msg.toByteArray(), 0, false)
        } catch ( e: MqttException) {
            e.printStackTrace()
        }
    }

}
