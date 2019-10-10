package com.app.aries.uiplayground.KotlinPlayground

import org.junit.Test

class TestNullability{
    @Test

    /* Kotlin */
    fun main(){
        var input: String? = "oh"
        // ... 1000 Lines Code
        //input = null
        // ... 1000 Lines Code
        val length = strLen(input!!)

        var mA : a= a()
        mA.isNull()

    }

    fun strLen(input:String) = input.length

    class a {
        fun isNull():Boolean = this == null
    }

    //fun strLen(input:String?) = input?.length

}


class TestTest(){

    fun main(){
        var hello : String? = null

        // ... 1000 Lines code ...

//        hello?.length
//        hello?.filter { ... }
//        hello?.plus( ... )
//        hello?...

        // ... 1000 Lines code ...
    }

//    fun main(){
//        var hello : String? = null
//
//        // ... 1000 Lines code ...
//
//        hello?.let{
//            hello.length
//            hello.filter { ... }
//            hello.plus( ... )
//            hello...
//        }
//
//        // ... 1000 Lines code ...
//    }

    class HaHaHa(val style : String){
//        var laughSound : String
        var laughSound : String? = null
        fun startLaugh(){
            if(style=="G8") laughSound = "HeyHeyHey"
        }
    }

}

class Str{
    var mStr = ""
}

class testRef{
    @Test
    fun main(){
//        var a = Str()
//        var b = Str()
//        var container = SubRefClass(a)
//        container.mStr.mStr = "GG"
//        println(a.mStr)
//        println(container.mStr.mStr)
//
//        changeStr(b).mStr="GGG"
//        println(b.mStr)

        var a : Int = 5
        var b : String = "GG"


        var x = List<Int>(10){ index-> index}
        var y = listOf<Int>()
        var z = listOf<Int>(1,2,3,4)

        var list = listOf<Int>()
        var mutableList = mutableListOf<Int>()





    }

    fun changeStr(input:Str):Str{
        //input = Str().apply{mStr = "changed!"}
        return input
    }
}

class SubRefClass(var mStr:Str){

}