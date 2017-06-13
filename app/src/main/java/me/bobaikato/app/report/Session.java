package me.bobaikato.app.report;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Author: Bobai Kato
 * Date: 6/10/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 */

public class Session {

    String ppsno = null, picture_path = null, encoded_string = null;
    private SharedPreferences sharedPreferences;

    public Session(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /*Default Constructor*/
    public Session() {
    }

    /*Login*/
    public void logout(String ppsno) {
        sharedPreferences.edit().remove(ppsno).commit();
    }

    public String getIdentity() {
        this.ppsno = sharedPreferences.getString("ppsno", "");
        return ppsno;
    }

    public boolean isIdentity() {
        return sharedPreferences.contains("ppsno");
    }

    public void setIdentity(String ppsno) {
        sharedPreferences.edit().putString("ppsno", ppsno).commit();
    }


    /*Picture path - don't forget to destroy when report is made*/

    public void destroy_picture_path(String picture_path) {
        sharedPreferences.edit().remove(picture_path).commit();
    }

    public String get_picture_path() {
        this.picture_path = sharedPreferences.getString("picture_path", "");
        return picture_path;
    }

    public boolean is_picture_path() {
        return sharedPreferences.contains("picture_path");
    }

    public void set_picture_path(String picture_path) {
        sharedPreferences.edit().putString("encoded_string", picture_path).commit();
    }


 /*Encoded String (Image) - don't forget to destroy when report is made*/

    public void destroy_encoded_string(String encoded_string) {
        sharedPreferences.edit().remove(encoded_string).commit();
    }

    public String get_encoded_string() {
        this.encoded_string = sharedPreferences.getString("encoded_string", "");
        return encoded_string;
    }

    public boolean is_encoded_string() {
        return sharedPreferences.contains("encoded_string");
    }

    public void set_encoded_string(String encoded_string) {
        sharedPreferences.edit().putString("encoded_string", encoded_string).commit();
    }


}
