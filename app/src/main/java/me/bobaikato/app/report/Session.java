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

    String ppsno = null;
    private SharedPreferences sharedPreferences;

    public Session(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /*Default Constructor*/
    public Session() {
    }

    public String getIdentity() {
        this.ppsno = sharedPreferences.getString("ppsno", "");
        return ppsno;
    }

    public void logout(String ppsno) {
        sharedPreferences.edit().remove(ppsno).commit();
    }

    public boolean isIdentity() {
        return sharedPreferences.contains("ppsno");
    }

    public void setIdentity(String ppsno) {
        sharedPreferences.edit().putString("ppsno", ppsno).commit();
    }
}
