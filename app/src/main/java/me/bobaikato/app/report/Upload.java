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
 */

public class Upload {

    private String encoded_string;

    Upload(String encoded_string) {
        this.encoded_string = encoded_string;

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
                    .add("encoded_string", encoded_string)
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
