package me.bobaikato.app.report;

import android.Manifest;
import android.content.Intent;
import android.graphics.Typeface;
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
    TextView acccident, crime, fireOutbreak, garbage, naturalDisaster, msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        /*Custome font*/
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font_1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");

        acccident = (TextView) findViewById(R.id.report_accident);
        crime = (TextView) findViewById(R.id.report_crime);
        fireOutbreak = (TextView) findViewById(R.id.report_fire_ourbreak);
        garbage = (TextView) findViewById(R.id.report_garbage);
        naturalDisaster = (TextView) findViewById(R.id.report_natural_disaster);
        msg = (TextView) findViewById(R.id.report_msg);

        /*Set Custom Font*/
        acccident.setTypeface(custom_font);
        crime.setTypeface(custom_font);
        fireOutbreak.setTypeface(custom_font);
        garbage.setTypeface(custom_font);
        naturalDisaster.setTypeface(custom_font);
        msg.setTypeface(custom_font_1);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Category.this, session.getIdentity(), Toast.LENGTH_LONG).show();
            }
        }, 2000);

        /*Check Location*/
        locationCheck();

    }


    @Override
    protected void onResume() {
        super.onResume();
         /*Check Location*/
        locationCheck();
    }

    private void locationCheck() {
            /*Check Location*/
        if (checkLocation(this) == false) {
            /*Snackbar*/
            Snackbar.make(findViewById(android.R.id.content), "ALLOW LOCATION TO CONTINUE!", Snackbar.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);

        }
    }


    /*Validate Location before you continue*/
    public void setReportCategoryDetails(View v) {
        setView_id(v.getId());
        startActivity(new Intent(Category.this, CategoryDetails.class));
    }
}
