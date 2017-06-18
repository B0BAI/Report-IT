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
    String user_type = null, cat_id = null;
    private SharedPreferences sharedPreferences;

    public Session(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /*Default Constructor*/
    public Session() {
    }

    /*Login*/
    public void logout(String ppsno, String user_type, String cat_id) {
        sharedPreferences.edit().remove(ppsno).commit();
        sharedPreferences.edit().remove(user_type).commit();
        sharedPreferences.edit().remove(cat_id).commit();
    }

    public String getPPSNO() {
        this.ppsno = sharedPreferences.getString("ppsno", "");
        return ppsno;
    }

    public String getUser_type() {
        this.user_type = sharedPreferences.getString("user_type", "");
        return user_type;
    }

    public String get_cat_id() {
        this.cat_id = sharedPreferences.getString("cat_id", "");
        return cat_id;
    }

    public boolean isPPSNO() {
        return sharedPreferences.contains("ppsno");
    }

    public boolean isUser_type() {
        return sharedPreferences.contains("user_type");
    }

    public void setIdentity(String ppsno, String user_type, String cat_id) {
        sharedPreferences.edit().putString("ppsno", ppsno).commit();
        sharedPreferences.edit().putString("user_type", user_type).commit();
        sharedPreferences.edit().putString("cat_id", cat_id).commit();
    }


}
