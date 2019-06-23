package com.app.aries.uiplayground.KotlinPlayground

import org.junit.Test

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

        var realA = A()
        realA.info="this is A"
        realA.myPrint()

        var fakeA = (A() as FakeA)
        fakeA.myPrint()
    }

}