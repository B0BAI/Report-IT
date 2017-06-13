package me.bobaikato.app.report;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class Summary extends AppCompatActivity {
    private static ImageView report_image;
    private String picture_path;

    public static void setBitmap(Bitmap bitmap) {
        report_image.setImageBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        report_image = (ImageView) findViewById(R.id.report_image);
    }
}
