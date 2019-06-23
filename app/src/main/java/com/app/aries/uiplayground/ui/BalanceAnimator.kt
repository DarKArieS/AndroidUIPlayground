package com.app.aries.uiplayground.ui

import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.Exception

class BalanceAnimator(
    private val textView:TextView,
    private val startNumber:Int,
    private val finalNumber:Int,
    private val totalFrame :Int = 50,
    private val longestUpdateRateMillis:Long = 70,
    decelerateFactor: Float = 1.2f
){
    private val interpolator = DecelerateInterpolator(decelerateFactor)

    private var observableList = CompositeDisposable()
    private val observable = Observable.create<Int>{
        var currentNumber: Int
        var frameInterval : Long
        val numberRange = finalNumber - startNumber

        for(currentFrame in 0..totalFrame){
            if (it.isDisposed) break
            val factor = interpolator.getInterpolation((currentFrame.toFloat())/totalFrame)
            currentNumber = startNumber + ( numberRange * factor ).toInt()
            frameInterval = (longestUpdateRateMillis * factor).toLong()
            it.onNext(currentNumber)
            try{
                //Thread may be canceled after dispose
                // TODO change to use delay operator
                Thread.sleep(frameInterval)
            } catch (e:Exception){ }
        }
        it.onComplete()
    }

    private val observer = object: Observer<Int> {
        override fun onNext(t: Int) {
            // update UI
            textView.text = "$ $t"
        }
        override fun onComplete() {}
        override fun onError(e: Throwable) {}
        override fun onSubscribe(d: Disposable) {observableList.add(d)}
    }

    fun startAnimation(){
        observable
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnDispose {
                Timber.tag("balanceAnimator").d("onDispose")
            }
            .subscribe(observer)
    }

    fun stopAnimation(){
        observableList.dispose()
    }
}