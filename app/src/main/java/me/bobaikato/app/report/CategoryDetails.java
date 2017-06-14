package me.bobaikato.app.report;
/**
 * Author: Bobai Kato
 * Date: 6/13/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 * Email: bobai.Kato@gmail.com
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import static me.bobaikato.app.report.Permissions.checkNetwork;
import static me.bobaikato.app.report.Summary.setCategory_id;
import static me.bobaikato.app.report.Summary.set_sum_properties;

public class CategoryDetails extends AppCompatActivity {


    private static Integer view_id;
    String picturePath, encoded_image;
    private Fonts fonts;
    private ImageView camera_icon;
    private TextView title, details, continueBTN;
    private LocationManager manager = null;
    private Bitmap bitmap;
    private Uri file_uri;
    private Session session;

    public static void setView_id(Integer view_id) {
        CategoryDetails.view_id = view_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        session = new Session(getApplicationContext());
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkGPS();
          /*Font*/
        fonts = new Fonts(getApplicationContext());

        title = (TextView) findViewById(R.id.report_title);
        details = (TextView) findViewById(R.id.report_details);
        camera_icon = (ImageView) findViewById(R.id.upload_camera);
        continueBTN = (TextView) findViewById(R.id.contn_btn);

        continueBTN.setVisibility(View.INVISIBLE);

        /*Custom font*/
        if (view_id == R.id.report_accident) {
            title.setText(getString(R.string.accident));
            details.setText(getString(R.string.accident_cat_det));
            setCategory_id(0);
        } else if (view_id == R.id.report_crime) {
            title.setText(getString(R.string.crime));
            details.setText(getString(R.string.crime_cat_det));
            setCategory_id(1);
        } else if (view_id == R.id.report_fire_ourbreak) {
            title.setText(getString(R.string.fire_outbreak));
            details.setText(getString(R.string.fire_outbreak_cat_det));
            setCategory_id(2);
        } else if (view_id == R.id.report_garbage) {
            title.setText(getString(R.string.garbage));
            details.setText(getString(R.string.garbage_cat_det));
            setCategory_id(3);
        } else if (view_id == R.id.report_natural_disaster) {
            title.setText(getString(R.string.natural_disaster));
            details.setText(getString(R.string.natural_disaster_cat_det));
            setCategory_id(4);
        }
        title.setTypeface(fonts.getCustom_font_1());
        details.setTypeface(fonts.getCustom_font());
        continueBTN.setTypeface(fonts.getCustom_font_1());

        if (savedInstanceState == null) {

        }


        camera_icon.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkGPS()) {
                    if (getApplicationContext().getPackageManager().hasSystemFeature(
                            PackageManager.FEATURE_CAMERA)) {
                        Bitmap bitmap = null;
                        Uri file_uri = null;
                        // Open default camera
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);

                        // start the camera_icon capture Intent
                        startActivityForResult(intent, 100);

                    } else {
                        Toast.makeText(getApplication(), R.string.cam_support_msg, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        continueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkGPS()) {
                    if (checkNetwork(v, getApplicationContext())) {
                        startActivity(new Intent(CategoryDetails.this, Summary.class));
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            file_uri = data.getData();
            bitmap = (Bitmap) data.getExtras().get("data");

            // Cursor to get camera_icon uri to display
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(file_uri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteFormat = stream.toByteArray();
            // get the base 64 string
            encoded_image = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
            continueBTN.setVisibility(View.VISIBLE);
            set_sum_properties(picturePath, encoded_image, bitmap);


        }
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
