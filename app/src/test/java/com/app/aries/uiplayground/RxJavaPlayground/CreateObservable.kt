package com.app.aries.uiplayground.RxJavaPlayground

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.junit.Test

class CreateObservable {

    val printObserver = object: Observer<String>{
        override fun onSubscribe(d: Disposable) {
            println("printObserver: onSubscribe")
        }

        override fun onNext(t: String) {
            println(t)
        }

        override fun onError(e: Throwable) {

        }

        override fun onComplete() {
            println("printObserver: onComplete")
        }


    }

    @Test
    fun run(){

        println("hi this is playground!")

    }


    data class User (
        var id:Int,
        var name:String
        )

    private fun createOperators(){
        // basic observable
        val observable = Observable.create<String> {  }

        //do not create the Observable until the observer subscribes, and create a fresh Observable for each observer
        val deferObservable = Observable.defer<String>{
            ObservableSource {
                it.onNext("next")
                it.onComplete()
            }
        }

        // complete normally
        val empty = Observable.empty<String>()

        // do nothing
        val never = Observable.never<String>()

        // throw error
        val mthrow = Observable.error<String> {  Exception("Error!")}

        // from array:
        // Converts an Array into an ObservableSource that emits the items in the Array.
        val stringArray = arrayOf("a","b","c")
        val from = Observable.fromArray(stringArray,stringArray)

        //from.subscribe(printObserver)
    }



}