package me.bobaikato.app.report;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author: Bobai Kato
 * Date: 6/13/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 * Email: bobai.Kato@gmail.com
 */

class Report {
    private Integer ID;
    private String ENCODED_IMAGE;
    private String CURRENT_TIME;
    private Integer CATEGORY_ID;
    private String USER_PPSNO;
    private String CURRENT_DATE;
    private String LONGITUDE;
    private String LATITUDE;
    private String MORE_DETAILS;
    private String ADDRESS;
    private String IMAGE_URL;
    private String CATEGORY_NAME;


    Report(Integer ID, String CURRENT_TIME, String CURRENT_DATE, String USER_PPSNO, String LONGITUDE, String LATITUDE, String CATEGORY_NAME, String MORE_DETAILS, String ADDRESS, String IMAGE_URL) {
        this.ID = ID;
        this.IMAGE_URL = IMAGE_URL;
        this.CURRENT_TIME = CURRENT_TIME;
        this.CURRENT_DATE = CURRENT_DATE;
        this.USER_PPSNO = USER_PPSNO;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.CATEGORY_NAME = CATEGORY_NAME;
        this.MORE_DETAILS = MORE_DETAILS;
        this.ADDRESS = ADDRESS;
    }

    Report(String ENCODED_IMAGE, String CURRENT_TIME, String CURRENT_DATE, String USER_PPSNO, String LONGITUDE, String LATITUDE, Integer CATEGORY_ID, String MORE_DETAILS, String ADDRESS) {
        this.ENCODED_IMAGE = ENCODED_IMAGE;
        this.CURRENT_TIME = CURRENT_TIME;
        this.CURRENT_DATE = CURRENT_DATE;
        this.USER_PPSNO = USER_PPSNO;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.CATEGORY_ID = CATEGORY_ID;
        this.MORE_DETAILS = MORE_DETAILS;
        this.ADDRESS = ADDRESS;

        new Action().execute();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Integer getID() {
        return ID;
    }

    public String getENCODED_IMAGE() {
        return ENCODED_IMAGE;
    }

    public String getCURRENT_TIME() {
        return CURRENT_TIME;
    }

    public Integer getCATEGORY_ID() {
        return CATEGORY_ID;
    }

    public String getUSER_PPSNO() {
        return USER_PPSNO;
    }

    public String getCURRENT_DATE() {
        return CURRENT_DATE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public String getMORE_DETAILS() {
        return MORE_DETAILS;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public String getCATEGORY_NAME() {
        return CATEGORY_NAME;
    }

    private class Action extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("encoded_image", ENCODED_IMAGE)
                    .add("category_id", CATEGORY_ID.toString())
                    .add("current_time", CURRENT_TIME)
                    .add("ppsno", USER_PPSNO)
                    .add("current_date", CURRENT_DATE)
                    .add("longitude", LONGITUDE)
                    .add("latitude", LATITUDE)
                    .add("info_details", MORE_DETAILS)
                    .add("address", ADDRESS)
                    .build();
            Request request = new Request.Builder()
                    .url("https://www.report.lastdaysmusic.com/report/upload.php")
                    .post(formBody)
                    .build();
            Response responses = null;
            try {
                client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
