package com.example.collection;

public class StringAndIntern {
    public static void main(String[] args) {
        String a = "this is case 1";
        String b = "this is case 1";
        System.out.println(a == b); // true

        String c = new String("this is case 2");
        String d = new String("this is case 2");
        System.out.println(c == d); // false

        String e = new String("this is case 3").intern();
        String f = new String("this is case 3").intern();
        System.out.println(e == f); // true

        String g = new String("this is case 4").intern();
        String h = "this is case 4";
        System.out.println(g == h); // true
    }
}