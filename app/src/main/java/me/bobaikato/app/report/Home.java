package me.bobaikato.app.report;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    TextView acccident, crime, fireOutbreak, garbage, naturalDisaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        acccident = (TextView) findViewById(R.id.report_accident);

    }

}
