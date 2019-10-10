package com.app.aries.uiplayground.JavaPlayground;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;


public class JavaPlayground {
    //@Test
    public void main(){
        String input = "oh";
//        ...
        input=null;
//        ...
        int length = strLen(input);
    }


//    String strLen(String input){
//        input += "hey hey hey ...";
//        return input;
//    }

    /* Java */
    private int strLen(@NotNull String input){
        return input.length();
    }

}


