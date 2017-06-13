package me.bobaikato.app.report;

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
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;

import java.text.SimpleDateFormat;
import java.util.Date;

import static me.bobaikato.app.report.Login.session;


public class Summary extends AppCompatActivity {
    private static ImageView report_pic, view_pic;
    private static String picture_path;
    private static String encoded_string;
    private static Bitmap newbitmap;
    private ImagePopup imagePopup;
    private TextView submitBtn, report_sum_heading, gps_co_long, gps_co_lat, cur_time, cur_date, user_id;
    private EditText more_details;
    private Fonts fonts;
    private LocationManager manager;
    private LocationListener listener;
    private ProgressDialog progress;
    private MediaPlayer mediaPlayer;


    public static void set_sum_properties(String pic_path, String encoded_str, Bitmap bitmap) {
        picture_path = pic_path;
        encoded_string = encoded_str;
        newbitmap = bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         /*Custom Font*/
        fonts = new Fonts(getApplicationContext());
        report_pic = (ImageView) findViewById(R.id.camera_shot_summary);
        view_pic = (ImageView) findViewById(R.id.view_shot);
        report_pic.setImageBitmap(newbitmap);
        report_pic.setVisibility(View.INVISIBLE);

        submitBtn = (TextView) findViewById(R.id.submit_btn);
        submitBtn.setVisibility(View.INVISIBLE);//hide button
        report_sum_heading = (TextView) findViewById(R.id.report_sum_header);
        gps_co_long = (TextView) findViewById(R.id.gps_long);
        gps_co_lat = (TextView) findViewById(R.id.gps_lat);
        cur_time = (TextView) findViewById(R.id.current_time);
        cur_date = (TextView) findViewById(R.id.current_date);
        user_id = (TextView) findViewById(R.id.user_id);
        more_details = (EditText) findViewById(R.id.more_details);
        /*set fonts*/
        submitBtn.setTypeface(fonts.getCustom_font_1());
        report_sum_heading.setTypeface(fonts.getCustom_font_1());
        gps_co_long.setTypeface(fonts.getCustom_font_1());
        gps_co_lat.setTypeface(fonts.getCustom_font_1());
        cur_time.setTypeface(fonts.getCustom_font_1());
        cur_date.setTypeface(fonts.getCustom_font_1());
        user_id.setTypeface(fonts.getCustom_font_1());
        more_details.setTypeface(fonts.getCustom_font());

        /*Image pop up*/
        imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(1000); // Optional
        imagePopup.setWindowWidth(1000); // Optional
        imagePopup.setBackgroundColor(Color.WHITE);  // Optional
        imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional

         /*Playing sound*/
        mediaPlayer = MediaPlayer.create(this, R.raw.gps_notification);

        view_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Initiate Popup view **/
                imagePopup.initiatePopup(report_pic.getDrawable());
            }
        });


          /*GPS*/
        checkGPS();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                gps_co_lat.setText("" + location.getLatitude());
                gps_co_long.setText("" + location.getLongitude());
                submitBtn.setVisibility(View.VISIBLE);//show button
                mediaPlayer.start();
                progress.dismiss();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Toast.makeText(Summary.this, "GPS status has changed", Toast.LENGTH_SHORT).show();
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
        cur_time.setText(new SimpleDateFormat("hh:mm a").format(new Date()).toUpperCase());
        cur_date.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        user_id.setText(session.getIdentity().toUpperCase());

        startGPS();
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
        progress = ProgressDialog.show(this, "GPS Request.",
                "Please wait while GPS Co-Ordinate loads...", true);

        /*Checking GPS*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        manager.requestLocationUpdates("gps", 3000, 0, listener);
    }
}
