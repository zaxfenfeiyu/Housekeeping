package com.example.ariel.housekeeping;

/**
 * Created by disagree on 2016/4/19.
 */
public class Data {


    private static String re_id="";

    private static Boolean ifLogin=false;
    private static String username="";
    private static String address="";

    public static Boolean getIfLogin() {
        return ifLogin;
    }

    public static void setIfLogin(Boolean ifLogin) {
        Data.ifLogin = ifLogin;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        Data.address = address;
    }

    public static String getUsername() {
        return username;
    }

    public  static void setUsername(String username) {
       Data.username = username;
    }

    public static String getRe_id() {
        return re_id;
    }
    public static void setRe_id(String re_id) {
        Data.re_id = re_id;
    }

}
