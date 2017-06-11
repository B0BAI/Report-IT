package me.bobaikato.app.report;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CategoryDetails extends AppCompatActivity {

    private static Integer view_id;
    private Fonts fonts;

    public static void setView_id(Integer view_id) {
        CategoryDetails.view_id = view_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


          /*Font*/
        fonts = new Fonts(getApplicationContext());

        TextView title = (TextView) findViewById(R.id.report_title);
        TextView details = (TextView) findViewById(R.id.report_details);

        /*Custom font*/
        if (view_id == R.id.report_accident) {
            title.setText(getString(R.string.accident));
            details.setText(getString(R.string.accident_cat_det));
        } else if (view_id == R.id.report_crime) {
            title.setText(getString(R.string.crime));
            details.setText(getString(R.string.crime_cat_det));
        } else if (view_id == R.id.report_fire_ourbreak) {
            title.setText(getString(R.string.fire_outbreak));
            details.setText(getString(R.string.fire_outbreak_cat_det));
        } else if (view_id == R.id.report_garbage) {
            title.setText(getString(R.string.garbage));
            details.setText(getString(R.string.garbage_cat_det));
        } else if (view_id == R.id.report_natural_disaster) {
            title.setText(getString(R.string.natural_disaster));
            details.setText(getString(R.string.natural_disaster_cat_det));
        }
        title.setTypeface(fonts.getCustom_font());
        details.setTypeface(fonts.getCustom_font());
    }
}
