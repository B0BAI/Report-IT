package me.bobaikato.app.report;
/**
 * Author: Bobai Kato
 * Date: 6/13/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 * Email: bobai.Kato@gmail.com
 */

import android.Manifest;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static me.bobaikato.app.report.CategoryDetails.setView_id;
import static me.bobaikato.app.report.Login.session;
import static me.bobaikato.app.report.Permissions.checkLocation;

public class Category extends AppCompatActivity {

    private TextView accident, crime, fire_out_break, garbage, natural_Disaster, msg, ppsno_id, logout;
    private Fonts fonts;
    private LocationManager manager = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        handleSession();
        /*Check Location*/
        locationCheck();

        /*Custom font*/
        fonts = new Fonts(getApplicationContext());

        accident = (TextView) findViewById(R.id.report_accident);
        crime = (TextView) findViewById(R.id.report_crime);
        fire_out_break = (TextView) findViewById(R.id.report_fire_ourbreak);
        garbage = (TextView) findViewById(R.id.report_garbage);
        natural_Disaster = (TextView) findViewById(R.id.report_natural_disaster);
        msg = (TextView) findViewById(R.id.report_msg);
        ppsno_id = (TextView) findViewById(R.id.ppsno_id);
        logout = (TextView) findViewById(R.id.logout);

        /*Set Custom Font*/
        accident.setTypeface(fonts.getCustom_font());
        crime.setTypeface(fonts.getCustom_font());
        fire_out_break.setTypeface(fonts.getCustom_font());
        garbage.setTypeface(fonts.getCustom_font());
        natural_Disaster.setTypeface(fonts.getCustom_font());
        msg.setTypeface(fonts.getCustom_font_1());
        logout.setTypeface(fonts.getCustom_font_1());
        ppsno_id.setTypeface(fonts.getCustom_font());
        ppsno_id.setText(session.getUser_type().toUpperCase() + " | " + session.getPPSNO().toUpperCase());
        /*Logout*/
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logout("ppsno", "user_type");
                startActivity(new Intent(Category.this, Login.class));
                Toast.makeText(Category.this, R.string.logout_msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    /*Handle session Redirect*/
    private void handleSession() {
        /*Check Session and redirect to Category*/
        if (!session.isPPSNO()) {
            startActivity(new Intent(Category.this, Login.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleSession();
         /*Check Location*/
        locationCheck();
    }

    private void locationCheck() {
            /*Check Location*/
        if (!checkLocation(this)) {
            /*Snackbar*/
            Snackbar.make(findViewById(android.R.id.content), R.string.allow_location_msg, Snackbar.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
        }
    }

    /*Validate Location before you continue*/
    public void setReportCategoryDetails(View v) {
        setView_id(v.getId());
        startActivity(new Intent(Category.this, CategoryDetails.class));
    }
}
