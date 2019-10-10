package com.app.aries.uiplayground.KotlinListPlayground

import org.junit.Test

class test{

    @Test
    fun main(){

        var a = getNewList().toMutableList()
        var b = getNewList().toMutableList()

        println(a===b)

        b = a

        println(a===b)



    }

    fun getNewList():List<String>{
        return List<String>(5){ it.toString() }
    }

    fun printList(input:List<String>){
        input.forEach { print(it)}
        println()
    }

}

class EmptyClass{

}


