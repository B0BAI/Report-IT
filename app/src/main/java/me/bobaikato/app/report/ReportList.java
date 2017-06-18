package me.bobaikato.app.report;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static me.bobaikato.app.report.Login.session;

public class ReportList extends AppCompatActivity {
    private static final String URL = " https://www.report.lastdaysmusic.com/report/list.php";
    public static ArrayList<Report> reportList;
    public static Report reportX;
    private JSONArray jsonarray;
    private String response_String;
    private String address, time, date, longitude, latitude, url, user, category, details;
    private JSONObject user_ppsno_JSON, time_JSON, date_JSON, long_JSON, lat_JSON, cat_name_JSON, details_JSON, address_JSON, image_url_JSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        new Action().execute();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        Toast.makeText(ReportList.this, reportList.get(1).getADDRESS(), Toast.LENGTH_LONG).show();
//                    }
//                }, 4000);
    }


    private class Action extends AsyncTask {

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            reportList = new ArrayList<Report>();
            for (int i = 0; i < jsonarray.length(); i++) {
                try {
                    time_JSON = jsonarray.getJSONObject(i);
                    date_JSON = jsonarray.getJSONObject(i);
                    long_JSON = jsonarray.getJSONObject(i);
                    lat_JSON = jsonarray.getJSONObject(i);
                    cat_name_JSON = jsonarray.getJSONObject(i);
                    details_JSON = jsonarray.getJSONObject(i);
                    address_JSON = jsonarray.getJSONObject(i);
                    image_url_JSON = jsonarray.getJSONObject(i);
                    user_ppsno_JSON = jsonarray.getJSONObject(i);

                    time = time_JSON.getString("time");
                    date = date_JSON.getString("date");
                    longitude = long_JSON.getString("longitude");
                    latitude = lat_JSON.getString("latitude");
                    url = image_url_JSON.getString("url");
                    user = user_ppsno_JSON.getString("user");
                    category = cat_name_JSON.getString("category");
                    address = address_JSON.getString("address");
                    details = details_JSON.getString("details");

                    reportX = new Report(i, time, date, user, longitude, latitude, category, details, address, url);
                    reportList.add(reportX);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
            Response responses = null;
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("category_id", session.get_cat_id())
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .post(formBody)
                    .build();

            try {
                responses = client.newCall(request).execute();
                response_String = responses.body().string();
                jsonarray = new JSONArray(response_String);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
