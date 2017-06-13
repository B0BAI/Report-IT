package me.bobaikato.app.report;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.ceylonlabs.imageviewpopup.ImagePopup;

import java.io.File;


public class Summary extends AppCompatActivity {
    private static ImageView report_pic;
    private static String picture_path;
    private static String encoded_string;
    private static Bitmap newbitmap;
    private File imgFile;
    private ImagePopup imagePopup;

    public static void set_sum_properties(String pic_path, String encoded_str, Bitmap bitmap) {
        picture_path = pic_path;
        encoded_string = encoded_str;
        newbitmap = bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        imgFile = new File(picture_path);
        report_pic = (ImageView) findViewById(R.id.camera_shot_summary);
        report_pic.setImageBitmap(newbitmap);

        /*Image pop up*/
        imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(1000); // Optional
        imagePopup.setWindowWidth(1000); // Optional
        imagePopup.setBackgroundColor(Color.WHITE);  // Optional
        imagePopup.setHideCloseIcon(false);  // Optional
        imagePopup.setImageOnClickClose(false);  // Optional

        report_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Initiate Popup view **/
                imagePopup.initiatePopup(report_pic.getDrawable());
            }
        });
    }
}
