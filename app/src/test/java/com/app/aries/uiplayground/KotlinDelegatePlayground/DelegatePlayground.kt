package com.app.aries.uiplayground.KotlinDelegatePlayground

import android.content.Context
import org.junit.Test
import kotlin.reflect.KProperty

class Elvis {
    var wannaSay: String by Delegate()
    //var wannaSay: String = "HeyHeyHey"
}

class Delegate {
    private var mValue = ""
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return mValue
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        //println("$value has been assigned to '${property.name}' in $thisRef.")
        mValue = "幹!$value"
    }
}

class DelegatePlayGround{

    @Test
    fun main(){
        var elvis = Elvis()
        elvis.wannaSay = "吃早餐囉"
        println(elvis.wannaSay)
        elvis.wannaSay = "吃午餐囉"
        println(elvis.wannaSay)
        elvis.wannaSay = "吃晚餐囉"
        println(elvis.wannaSay)
    }
}


object EagerSingleton{
    fun singleFuc() {}
}

class LazySingleton{
    companion object {
//        val instance: LazySingleton
//                        by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { LazySingleton() }
        var instance : String = "oh ya"
    }
}

class JavaLikeLazySingleton(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: JavaLikeLazySingleton? = null

        fun getInstance(context:Context) : JavaLikeLazySingleton =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: JavaLikeLazySingleton(context.applicationContext)
                        .also { INSTANCE = it }
            }
    }
}