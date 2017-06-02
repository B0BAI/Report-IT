package me.bobaikato.app.report;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Category extends AppCompatActivity {
    TextView title, cameraTitle, categoryDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

                /*Font*/
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font_1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");

        title = (TextView) findViewById(R.id.upload_title);
        cameraTitle = (TextView) findViewById(R.id.cat_title);
        categoryDetails  = (TextView) findViewById(R.id.cat_details);

        /*Custom font*/
        title.setTypeface(custom_font_1);
        categoryDetails.setTypeface(custom_font_1);
    }


}
