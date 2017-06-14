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

public class Upload {

    private String ENCODED_IMAGE;
    private String CURRENT_TIME;
    private Integer CATEGORY_ID;
    private String PPSNO;
    private String CURRENT_DATE;
    private String LONGITUDE;
    private String LATITUDE;


    Upload(String ENCODED_IMAGE, String CURRENT_TIME, String CURRENT_DATE, String PPSNO, String LONGITUDE, String LATITUDE, Integer CATEGORY_ID) {
        this.ENCODED_IMAGE = ENCODED_IMAGE;
        this.CURRENT_TIME = CURRENT_TIME;
        this.CURRENT_DATE = CURRENT_DATE;
        this.PPSNO = PPSNO;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.CATEGORY_ID = CATEGORY_ID;

        new Action().execute();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private class Action extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("encoded_image", ENCODED_IMAGE)
                    .add("category_id", CATEGORY_ID.toString())
                    .add("current_time", CURRENT_TIME)
                    .add("ppsno", PPSNO)
                    .add("current_date", CURRENT_DATE)
                    .add("longitude", LONGITUDE)
                    .add("latitude", LATITUDE)
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
