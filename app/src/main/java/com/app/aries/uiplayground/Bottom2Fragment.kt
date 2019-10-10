package com.app.aries.uiplayground


import android.app.NotificationChannel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.aries.uiplayground.navigationmanager.FragmentFactory
import com.app.aries.uiplayground.service.MyIntentService
import com.app.aries.uiplayground.service.MyService
import kotlinx.android.synthetic.main.fragment_bottom2.view.*
import timber.log.Timber
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.ServiceConnection
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import android.os.Build
import android.os.IBinder
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import androidx.core.app.NotificationManagerCompat
import com.app.aries.uiplayground.service.MyMQTTService
import com.app.aries.uiplayground.ui.BalanceAnimator
import kotlinx.android.synthetic.main.mqtt_test_layout.view.*
import kotlinx.android.synthetic.main.seek_bar_anim_pratice.view.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Bottom2Fragment : Fragment() {
    lateinit var rootView : View
    private var mainActivity : MainActivity? = null

    init{
        Timber.tag("lifecycle").d("Bottom2Fragment created")
    }
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onAttach(context: Context) {
        if (context is MainActivity) {
            mainActivity = (this.activity as MainActivity)
        }else mainActivity = null
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_bottom2, container, false)

        rootView.intentServiceButton.setOnClickListener { clickIntentServiceButton() }
        rootView.intentServiceButton2.setOnClickListener { clickIntentServiceButton2() }
        rootView.serviceButton.setOnClickListener { clickServiceButton() }
        rootView.stopServiceButton.setOnClickListener { clickStopServiceButton() }
        rootView.sendNotificationButton.setOnClickListener { clickSendNotification() }
        rootView.startMQTTServiceButton.setOnClickListener {  clickStartMQTTService()}
        rootView.publishMQTTButton.setOnClickListener { clickMQTTPublish() }

        setupEditText()
        setupSeekBar()
        startBalanceAnimation()
        return rootView
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if(!hidden){
            startBalanceAnimation()
        }else{
            stopBalanceAnimation()
        }
        super.onHiddenChanged(hidden)
    }

    override fun onDestroy() {
        Timber.tag("lifecycle").d("Bottom2Fragment onDestroy!")
        super.onDestroy()
    }

    companion object: FragmentFactory {
        @JvmStatic
        override fun newInstance() = Bottom2Fragment()
    }

    private lateinit var balanceAnimator : BalanceAnimator
    private fun startBalanceAnimation(){
        balanceAnimator = BalanceAnimator(
            rootView.balance,
            0,
            1000
        )
        balanceAnimator.startAnimation()
    }

    private fun stopBalanceAnimation(){
        balanceAnimator.stopAnimation()
    }


    var amount = 0f
    private fun setupSeekBar(){
        //rootView.discountSeekBar.min = 0
        rootView.discountSeekBar.max = 10000

        rootView.discountSeekBar.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Timber.tag("discountSys").d("$progress")
                updateDiscount(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        rootView.discountSeekBar.progress = 5000

    }

    private fun setupEditText(){
        rootView.inputAmount.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                Timber.tag("discountSys").d("loose focus!")

                when{
                    (rootView.inputAmount.editableText.toString() == "")->{
                        amount = 0f
                        rootView.inputAmount.setText("")
                    }

                    (rootView.inputAmount.editableText.toString().toInt()==0)->{
                        amount = 0f
                        rootView.inputAmount.setText("")
                    }

                    else->{
                        amount = rootView.inputAmount.editableText.toString().toFloat() // change 0100 to
                        rootView.inputAmount.setText("$ ${amount.toInt()}")
                    }
                }

                updateDiscount(rootView.discountSeekBar.progress)
            }else{
                if (amount!=0f)
                    rootView.inputAmount.setText("${amount.toInt()}")
                else
                    rootView.inputAmount.setText("")
            }
        }

        rootView.inputAmount.setOnEditorActionListener { v, actionId, event ->
            if(actionId ==  EditorInfo.IME_ACTION_DONE){
                //Timber.tag("discountSys").d(v.toString())
                v.clearFocus()
                //Timber.tag("discountSys").d( mainActivity?.currentFocus.toString() )
                val im: InputMethodManager =  mainActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }

            true
        }
    }

    private fun updateDiscount(progress:Int){
        val discountFactor = 1f - (progress.toFloat() /10000)
        rootView.discountPercentNumber.text = "%.2f".format(progress.toFloat() /100)



        if (rootView.inputAmount.editableText.toString() != ""){
            rootView.afterDiscountNumber.text = "$ %.2f".format(amount * discountFactor)
        }else{
            rootView.afterDiscountNumber.text = "0"
        }
    }

    private fun clickIntentServiceButton(){
        val intent = Intent(mainActivity, MyIntentService::class.java)
        intent.action = MyIntentService.ACTION_BAZ

//        val intent = Intent("com.app.aries.uiplayground.service.action.FOO")
//        intent.putExtra("com.app.aries.uiplayground.service.extra.PARAM1","1")
//        intent.putExtra("com.app.aries.uiplayground.service.extra.PARAM2","2")

//        val intent = Intent("com.app.aries.uiplayground.service.action.BAZ")
        mainActivity?.startService(intent)
    }

    private fun clickIntentServiceButton2(){
        val intent = Intent(mainActivity, MyIntentService::class.java)
        intent.action = MyIntentService.ACTION_FOO
        intent.putExtra(MyIntentService.EXTRA_PARAM1,"d")
        intent.putExtra(MyIntentService.EXTRA_PARAM2,50)
        mainActivity?.startService(intent)
    }

    private fun clickServiceButton(){
        val intent = Intent(mainActivity, MyService::class.java)

        mainActivity?.startService(intent)
    }

    private fun clickStopServiceButton(){
        val intent = Intent(mainActivity, MyService::class.java)
        mainActivity?.stopService(intent)
    }

    private fun clickSendNotification(){
        Timber.tag("NotificationTest").d("click to send!")

        val context = (activity as Context)
        //val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        val mNotificationManager = NotificationManagerCompat.from(context)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("Test_Notification","Test Notification",NotificationManager.IMPORTANCE_HIGH)

            mNotificationManager!!.createNotificationChannel(channel)
        }

        val mBuilder = NotificationCompat.Builder(context,"Test_Notification")
            .setSmallIcon(R.drawable.ic_edit_white_24dp)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_MAX)


        // Creates an explicit intent for an Activity in your app
        val resultIntent = Intent(context, MainActivity::class.java)

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        val stackBuilder = TaskStackBuilder.create(context)

        // Adds the back stack for the Intent (but not the Intent itself)
        //stackBuilder.addParentStack(MainActivity::class.java)

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(
            0,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        mBuilder.setContentIntent(resultPendingIntent)
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)


        // mId allows you to update the notification later on.
        mNotificationManager!!.notify(1, mBuilder.build())
    }


    // ==================== MQTT Service playground ===============================

    override fun onPause() {
        unbindNotificationService()
        super.onPause()
    }

    override fun onResume() {
        bindNotificationService()
        super.onResume()
    }

    private var mService: MyMQTTService? = null
    private var mBinder: MyMQTTService.ServiceBinder? = null
    private var hasBounded = false

    private val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBinder = service as MyMQTTService.ServiceBinder
            mService = service.getService()
            hasBounded = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            clearServiceMember()
        }
    }

    private fun clickStartMQTTService(){
        val intent = Intent(mainActivity, MyMQTTService::class.java)
        mainActivity?.startService(intent)
    }

    private fun bindNotificationService(){
        val intent = Intent(this.context, MyMQTTService::class.java)
        this.context?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unbindNotificationService(){
        if(mService!=null) this.context?.unbindService(serviceConnection)
        clearServiceMember()
    }

    private fun clearServiceMember(){
        mBinder = null
        mService = null
        hasBounded = false
    }

    private fun clickMQTTPublish(){
        if(null!=mBinder) mBinder!!.getService().publish("Publish something from client!")
    }
}
