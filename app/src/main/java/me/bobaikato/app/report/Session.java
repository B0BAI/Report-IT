package me.bobaikato.app.report;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Author: Bobai Kato
 * Date: 6/10/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 * Email: bobai.Kato@gmail.com
 */

public class Session {

    String ppsno = null;
    String user_type = null;
    private SharedPreferences sharedPreferences;

    public Session(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /*Default Constructor*/
    public Session() {
    }

    /*Login*/
    public void logout(String ppsno, String user_type) {
        sharedPreferences.edit().remove(ppsno).commit();
        sharedPreferences.edit().remove(user_type).commit();
    }

    public String getPPSNO() {
        this.ppsno = sharedPreferences.getString("ppsno", "");
        return ppsno;
    }

    public String getUser_type() {
        this.user_type = sharedPreferences.getString("user_type", "");
        return user_type;
    }

    public boolean isPPSNO() {
        return sharedPreferences.contains("ppsno");
    }

    public boolean isUser_type() {
        return sharedPreferences.contains("user_type");
    }

    public void setIdentity(String ppsno, String user_type) {
        sharedPreferences.edit().putString("ppsno", ppsno).commit();
        sharedPreferences.edit().putString("user_type", user_type).commit();
    }


}
