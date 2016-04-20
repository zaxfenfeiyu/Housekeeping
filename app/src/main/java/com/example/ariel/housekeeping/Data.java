package com.example.ariel.housekeeping;

/**
 * Created by disagree on 2016/4/19.
 */
public class Data {
    private static Boolean ifLogin=false;
    private static String username="";

    public static String getUsername() {
        return username;
    }

    public  static void setUsername(String username) {
       Data.username = username;
    }
}
