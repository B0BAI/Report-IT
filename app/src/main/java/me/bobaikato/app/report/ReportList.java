package me.bobaikato.app.report;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static me.bobaikato.app.report.Login.session;
import static me.bobaikato.app.report.Maps.setIDs;

public class ReportList extends AppCompatActivity {
    private static final String URL = " https://www.report.lastd*****sic.com/report/list.php";
    public static List<Report> reportList;
    private JSONArray jsonarray;
    private String response_String;
    private String address, time, date, longitude, latitude, url, user, category, details;
    private JSONObject user_ppsno_JSON, time_JSON, date_JSON, long_JSON, lat_JSON, cat_name_JSON, details_JSON, address_JSON, image_url_JSON;

    private TextView logout, add0, add1, add2, date0, date1, date2, time0, time1, time2, admin_title;
    private ImageView hide1, hide2, hide3;
    private ImagePopup imagePopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        logout = (TextView) findViewById(R.id.logout1);
           /*Logout*/
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logout("ppsno", "user_type", "cat_id");
                startActivity(new Intent(ReportList.this, Login.class));
                Toast.makeText(ReportList.this, R.string.logout_msg, Toast.LENGTH_LONG).show();
            }
        });



          /*Image pop up*/
        imagePopup = new ImagePopup(ReportList.this);
        imagePopup.setBackgroundColor(Color.WHITE);  // Optional
        imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional


        add0 = (TextView) findViewById(R.id.address_1);
        add1 = (TextView) findViewById(R.id.address_2);
        add2 = (TextView) findViewById(R.id.address_3);


        time0 = (TextView) findViewById(R.id.time_1);
        time1 = (TextView) findViewById(R.id.time_2);
        time2 = (TextView) findViewById(R.id.time_3);


        date0 = (TextView) findViewById(R.id.date_1);
        date1 = (TextView) findViewById(R.id.date_2);
        date2 = (TextView) findViewById(R.id.date_3);

        hide1 = (ImageView) findViewById(R.id.hide_1);
        hide2 = (ImageView) findViewById(R.id.hide_2);
        hide3 = (ImageView) findViewById(R.id.hide_3);

        hide1.setVisibility(View.INVISIBLE);
        hide2.setVisibility(View.INVISIBLE);
        hide3.setVisibility(View.INVISIBLE);

        admin_title = (TextView) findViewById(R.id.admin_title);
        admin_title.setText(session.getUser_type().toUpperCase() + " OFFICIAL");


        new Action().execute();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void showReportImage(View v) {
        Integer id = v.getId();
        if (id == R.id.image_1) {
            imagePopup.initiatePopup(hide1.getDrawable());
        } else if (id == R.id.image_2) {
            imagePopup.initiatePopup(hide2.getDrawable());
        } else if (id == R.id.image_3) {
            imagePopup.initiatePopup(hide3.getDrawable());
        }
    }

    public void showOnMap(View v) {
        Integer id = v.getId();
        if (id == R.id.pin_1) {
            setIDs(Double.parseDouble(reportList.get(0).getLONGITUDE()), Double.parseDouble(reportList.get(0).getLATITUDE()), reportList.get(0).getMORE_DETAILS());
            startActivity(new Intent(ReportList.this, Maps.class));
        } else if (id == R.id.pin_2) {
            setIDs(Double.parseDouble(reportList.get(1).getLONGITUDE()), Double.parseDouble(reportList.get(1).getLATITUDE()), reportList.get(1).getMORE_DETAILS());
            startActivity(new Intent(ReportList.this, Maps.class));
        } else if (id == R.id.pin_3) {
            setIDs(Double.parseDouble(reportList.get(2).getLONGITUDE()), Double.parseDouble(reportList.get(2).getLATITUDE()), reportList.get(2).getMORE_DETAILS());
            startActivity(new Intent(ReportList.this, Maps.class));
        }
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
                    reportList.add(new Report(i, time, date, user, longitude, latitude, category, details, address, url));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            /*Set Address*/
            add0.setText(reportList.get(0).getADDRESS());
            add1.setText(reportList.get(1).getADDRESS());
            add2.setText(reportList.get(2).getADDRESS());


            /*Set Time*/
            time0.setText(reportList.get(0).getCURRENT_TIME());
            time1.setText(reportList.get(1).getCURRENT_TIME());
            time2.setText(reportList.get(2).getCURRENT_TIME());


            /*Set Date*/
            date0.setText(reportList.get(0).getCURRENT_DATE());
            date1.setText(reportList.get(1).getCURRENT_DATE());
            date2.setText(reportList.get(2).getCURRENT_DATE());

            /*LOAD IMAGE*/
            Picasso.with(getApplicationContext()).load(reportList.get(0).getIMAGE_URL()).into(hide1);
            Picasso.with(getApplicationContext()).load(reportList.get(1).getIMAGE_URL()).into(hide2);
            Picasso.with(getApplicationContext()).load(reportList.get(2).getIMAGE_URL()).into(hide3);


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
