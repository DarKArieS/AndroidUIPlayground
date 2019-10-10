package com.app.aries.uiplayground.KotlinPlayground

import org.junit.Test
import kotlin.reflect.KProperty

interface printer{
    fun myPrint()
}

open class A : printer{
    open var info = ""
    override fun myPrint(){
        println("yup!" + info)
    }

}

class FakeA : A(){
    override var info = ""
    override fun myPrint(){
        println("fake yup!" + info)
    }
}

class test{

    @Test
    fun main(){

//        var realA = A()
//        realA.info="this is A"
//        realA.myPrint()
//
//        var fakeA = (A() as FakeA)
//        fakeA.myPrint()
    }

}

class DelegatePlayGround(){

    //@Test
    fun main(){
        val OriginSoG8 = G8Class()
        val ModifiedG8 : G8Class by G8Class2()

        val OriginSoG8_2 = G8Class()

//        callSoG8Class(OriginSoG8)
//        callSoG8Class(ModifiedG8)
//        ModifiedG8.ImSoG8No2()
    }

    fun callSoG8Class(input:G8Class){
        input.ImSoG8()
        input.ImSoG8No2()
    }

    fun returnNothing():Nothing{
        throw Exception("your program GG")
    }

}

class Example {
    var p: String by Delegate()
}

class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

class G8Class{
    fun ImSoG8(){
        println("ImSoG8")
    }
    fun ImSoG8No2(){
        println("ImSoG8-No2")
    }
}

class G8Class2{
    operator fun getValue(thisRef: Any?, property: KProperty<*>):G8Class{
        return G8Class()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: G8Class) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }

    fun ImSoG8(){
        println("Im not So G8")
    }
}

class equalsPlayground(){

    @Test
    fun main(){
        val a = myClass()
        val b = myClass()
        val c = a

        println((a==b))

        println((a==c))

        println((a===b))
        println((a===c))
    }

}
data class myClass(
    var a :Int=0,
    var b :Int=1){

}