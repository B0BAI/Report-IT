package me.bobaikato.app.report;
/*
 * Author: Bobai Kato
 * Date: 6/13/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 * Email: bobai.Kato@gmail.com
 */

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Newspager;
import static me.bobaikato.app.report.Login.session;
import static me.bobaikato.app.report.Permissions.checkLocation;


public class Summary extends AppCompatActivity {
    private static String picture_path, report_details;
    private static String encoded_string;
    private static Bitmap newbitmap;
    private static Integer category_id;
    private static String URL = null;
    private ImageView report_pic, view_pic;
    private ImagePopup imagePopup;
    private TextView submitBtn, report_address, report_sum_heading, gps_codinate, cur_time, cur_date, user_id;
    private EditText more_details;
    private Fonts fonts;
    private LocationManager manager;
    private LocationListener listener;
    private MediaPlayer mediaPlayer;
    private Integer NOTIFICATION_FLAG = 0;
    private String longitude, latitude;
    private NiftyDialogBuilder dialogBuilder;
    private ProgressDialog progressDialog;
    private String address;


    public static void setCategory_id(Integer category_id) {
        Summary.category_id = category_id;
    }

//            new Report(encoded_image);

    public static void set_sum_properties(String pic_path, String encoded_str, Bitmap bitmap) {
        picture_path = pic_path;
        encoded_string = encoded_str;
        newbitmap = bitmap;
    }

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        /*DIALOGUE & POP UP*/
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        progressDialog = new ProgressDialog(Summary.this,
                R.style.AppTheme_Dark_Dialog);
         /*Custom Font*/
        fonts = new Fonts(getApplicationContext());
        report_pic = (ImageView) findViewById(R.id.camera_shot_summary);
        view_pic = (ImageView) findViewById(R.id.view_shot);
        report_pic.setImageBitmap(newbitmap);
        report_pic.setVisibility(View.INVISIBLE);

        submitBtn = (TextView) findViewById(R.id.submit_btn);
        submitBtn.setVisibility(View.INVISIBLE);//hide button
        report_sum_heading = (TextView) findViewById(R.id.report_sum_header);
        gps_codinate = (TextView) findViewById(R.id.gps_lat);
        cur_time = (TextView) findViewById(R.id.current_time);
        cur_date = (TextView) findViewById(R.id.current_date);
        user_id = (TextView) findViewById(R.id.user_id);
        report_address = (TextView) findViewById(R.id.report_address);
        more_details = (EditText) findViewById(R.id.more_details);
        /*set fonts*/
        submitBtn.setTypeface(fonts.getCustom_font_1());
        report_sum_heading.setTypeface(fonts.getCustom_font_1());
        gps_codinate.setTypeface(fonts.getCustom_font_1());
        cur_time.setTypeface(fonts.getCustom_font_1());
        cur_date.setTypeface(fonts.getCustom_font_1());
        user_id.setTypeface(fonts.getCustom_font_1());
        report_address.setTypeface(fonts.getCustom_font_1());
        more_details.setTypeface(fonts.getCustom_font());

        /*Image pop up*/
        imagePopup = new ImagePopup(Summary.this);
        imagePopup.setBackgroundColor(Color.WHITE);  // Optional
        imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional

         /*Playing sound*/
        mediaPlayer = MediaPlayer.create(this, R.raw.gps_notification);

        view_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Initiate Popup view */
                imagePopup.initiatePopup(report_pic.getDrawable());
            }
        });

          /*GPS */
        checkGPS();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = "" + location.getLatitude();
                longitude = "" + location.getLongitude();
                gps_codinate.setText(longitude + ", " + latitude);

                if (NOTIFICATION_FLAG == 0) {
                    mediaPlayer.start();
                    ++NOTIFICATION_FLAG;
                }
                new Action().execute();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                //Toast.makeText(Summary.this, R.string.gps_update_msg, Toast.LENGTH_SHORT).show();
                ++NOTIFICATION_FLAG;
                new Action().execute();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        };

        /*Set Variables*/
        cur_time.setText(new SimpleDateFormat(getString(R.string.time_format)).format(new Date()).toUpperCase());
        cur_date.setText(new SimpleDateFormat(getString(R.string.date_format)).format(new Date()));
        user_id.setText(session.getPPSNO().toUpperCase());
        startGPS();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       /*Check Location approval*/
                if (!checkLocation(getApplicationContext())) {
                    ActivityCompat.requestPermissions(Summary.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            99);
                }
                manager.removeUpdates(listener);
                report_details = more_details.getText().toString().trim();
                if ((report_details.length() < 1) || (report_details.isEmpty())) {
                    report_details = "NULL";
                }
                new Report(encoded_string, new SimpleDateFormat(getString(R.string.time_format)).format(new Date()), new SimpleDateFormat(getString(R.string.date_format)).format(new Date()), session.getPPSNO(), longitude, latitude, category_id, report_details, address);

                /*RESET Fields*/

                gps_codinate.setText("-0.000000");
                cur_date.setText("00:00");
                cur_time.setText("00-00-0000");
                user_id.setText("00000000000");
                more_details.setText("00000000000");
                report_address.setText("00000000000");

                /*POP UP*/
                dialogBuilder
                        .withTitle(getString(R.string.report_sent))
                        .withDividerColor("#FFFFFF")
                        .withMessage(getString(R.string.report_success_msg))
                        .withMessageColor("#FFFFFF")
                        .withDialogColor(R.color.black)
                        .withEffect(Newspager)
                        .isCancelableOnTouchOutside(false)
                        .show();
                /*Delays*/
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                dialogBuilder.dismiss();
                                finish();//kill the activity totaly
                                startActivity(new Intent(Summary.this, Category.class));
                            }
                        }, 5000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.removeUpdates(listener);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                startGPS();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkGPS();
    }

    private boolean checkGPS() {
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), R.string.gps_msg, Snackbar.LENGTH_INDEFINITE);
            snackBar.setAction("TURN ON", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }).show();
            return false;
        } else {
            return true;
        }
    }

    private void startGPS() {
        /*Dailog*/
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.finding_gps_nsg));
        progressDialog.show();

        /*Checking GPS*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1500, 0, listener);
    }


    private class Action extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=AIzaSyCmewO09gXfOzLyzXX9Kl3QDXx9kdYwCYI";
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
                   /*Delays*/
            report_address.setText(address);
            submitBtn.setVisibility(View.VISIBLE);//show button
            progressDialog.dismiss();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(URL)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();

                String jsonData = responses.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                JSONArray Jarray = Jobject.getJSONArray("results");
                JSONObject object = Jarray.getJSONObject(0);
                address = object.getString("formatted_address");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
