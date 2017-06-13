package me.bobaikato.app.report;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylonlabs.imageviewpopup.ImagePopup;


public class Summary extends AppCompatActivity {
    private static ImageView report_pic, view_pic;
    private static String picture_path;
    private static String encoded_string;
    private static Bitmap newbitmap;
    private ImagePopup imagePopup;
    private TextView submitBtn, report_sum_heading, gps_coordinate, cur_time, cur_date, user_id;
    private EditText more_details;
    private Fonts fonts;
    private LocationManager manager;

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
        checkGPS();

        report_pic = (ImageView) findViewById(R.id.camera_shot_summary);
        view_pic = (ImageView) findViewById(R.id.view_shot);
        report_pic.setImageBitmap(newbitmap);
        report_pic.setVisibility(View.INVISIBLE);

        submitBtn = (TextView) findViewById(R.id.submit_btn);
        report_sum_heading = (TextView) findViewById(R.id.report_sum_header);
        gps_coordinate = (TextView) findViewById(R.id.gps_co_ordinate);
        cur_time = (TextView) findViewById(R.id.current_time);
        cur_date = (TextView) findViewById(R.id.current_date);
        user_id = (TextView) findViewById(R.id.user_id);
        more_details = (EditText) findViewById(R.id.more_details);
        /*set fonts*/
        submitBtn.setTypeface(fonts.getCustom_font_1());
        report_sum_heading.setTypeface(fonts.getCustom_font_1());
        gps_coordinate.setTypeface(fonts.getCustom_font());
        cur_time.setTypeface(fonts.getCustom_font());
        cur_date.setTypeface(fonts.getCustom_font());
        user_id.setTypeface(fonts.getCustom_font());
        more_details.setTypeface(fonts.getCustom_font());


        /*Image pop up*/
        imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(1000); // Optional
        imagePopup.setWindowWidth(1000); // Optional
        imagePopup.setBackgroundColor(Color.WHITE);  // Optional
        imagePopup.setHideCloseIcon(false);  // Optional
        imagePopup.setImageOnClickClose(false);  // Optional

        /*Image Popup*/
        view_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Initiate Popup view **/
                imagePopup.initiatePopup(report_pic.getDrawable());
            }
        });
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
}
