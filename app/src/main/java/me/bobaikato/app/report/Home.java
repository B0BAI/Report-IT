package me.bobaikato.app.report;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    TextView acccident, crime, fireOutbreak, garbage, naturalDisaster,msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

                     /*Check InternetConnection Connection*/
        new InternetConnection(findViewById(android.R.id.content), Home.this);
    }

}
