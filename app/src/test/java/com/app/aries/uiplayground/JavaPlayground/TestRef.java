package com.app.aries.uiplayground.JavaPlayground;

import org.junit.Test;

class Str{
    String mStr = "";

    void Str(String input){
        mStr = input;
    }
}


public class TestRef{

    @Test
    public void main(){
        Str a = new Str();
        Str b = new Str();

        Str c = changeStr(a);
        System.out.println("a:" + a.mStr);
        System.out.println("c:" + c.mStr);

        TestRefClass container = new TestRefClass(a);
        container.mStr.mStr="heyheyhey";
        System.out.println("a:" + a.mStr);
        System.out.println("container:" + container.mStr.mStr);

        Str d = a;
        d.mStr = "gg";
        System.out.println("a:" + a.mStr);
        System.out.println("d:" + d.mStr);

        System.out.println("a:" + a.hashCode());
        System.out.println("d:" + d.hashCode());

        int A = 5;
        int B = A;
        System.out.println("a:" + A);
        System.out.println("d:" + B);

        Integer aI = new Integer(5);
        Integer bI = aI;

        System.out.println("aI:" + aI);
        System.out.println("bI:" + bI.hashCode());


    }

    Str changeStr(Str input){
        input = new Str();
        input.mStr = "changed!";
        return input;
    }

}


class TestRefClass{
    Str mStr;
    TestRefClass(Str input){
        mStr = input;
    }
}