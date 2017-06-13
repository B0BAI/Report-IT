package me.bobaikato.app.report;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylonlabs.imageviewpopup.ImagePopup;

import java.io.File;


public class Summary extends AppCompatActivity {
    private static ImageView report_pic;
    private static String picture_path;
    private static String encoded_string;
    private static Bitmap newbitmap;
    private File imgFile;
    private ImagePopup imagePopup;
    private TextView submitBtn;
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
         /*Custom Font*/
        fonts = new Fonts(getApplicationContext());

        imgFile = new File(picture_path);
        report_pic = (ImageView) findViewById(R.id.camera_shot_summary);
        report_pic.setImageBitmap(newbitmap);
        submitBtn = (TextView) findViewById(R.id.submit_btn);
        submitBtn.setTypeface(fonts.getCustom_font_1());
        /*Image pop up*/
        imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(1000); // Optional
        imagePopup.setWindowWidth(1000); // Optional
        imagePopup.setBackgroundColor(Color.WHITE);  // Optional
        imagePopup.setHideCloseIcon(false);  // Optional
        imagePopup.setImageOnClickClose(false);  // Optional

        /*Image Popup*/
        report_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Initiate Popup view **/
                imagePopup.initiatePopup(report_pic.getDrawable());
            }
        });
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
