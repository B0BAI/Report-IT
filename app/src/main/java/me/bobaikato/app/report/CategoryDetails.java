package me.bobaikato.app.report;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import static me.bobaikato.app.report.Login.session;

public class CategoryDetails extends AppCompatActivity {


    private static Integer view_id;
    String picturePath;
    private Fonts fonts;
    private ImageView camera_icon;
    private TextView title, details, continue_btn;
    private LocationManager manager;
    private Bitmap bitmap;
    private Uri file_uri;
    private Integer continue_btn_flag = 0;


    public static void setView_id(Integer view_id) {
        CategoryDetails.view_id = view_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkGPS();
          /*Font*/
        fonts = new Fonts(getApplicationContext());

        title = (TextView) findViewById(R.id.report_title);
        details = (TextView) findViewById(R.id.report_details);
        camera_icon = (ImageView) findViewById(R.id.upload_camera);
        continue_btn = (TextView) findViewById(R.id.continue_btn);
        continue_btn.setVisibility(View.INVISIBLE);

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
        title.setTypeface(fonts.getCustom_font_1());
        details.setTypeface(fonts.getCustom_font());

        if (savedInstanceState == null) {

        }


//        final ImagePopup imagePopup = new ImagePopup(this);
//        imagePopup.setWindowHeight(800); // Optional
//        imagePopup.setWindowWidth(800); // Optional
//        imagePopup.setBackgroundColor(Color.BLACK);  // Optional
//        imagePopup.setHideCloseIcon(true);  // Optional
//        imagePopup.setImageOnClickClose(true);  // Optional
//
//
//        camera_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /** Initiate Popup view **/
//                imagePopup.initiatePopup(camera_icon.getDrawable());
//            }
//        });

        camera_icon.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkGPS()) {
                    if (getApplicationContext().getPackageManager().hasSystemFeature(
                            PackageManager.FEATURE_CAMERA)) {
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
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
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

            //bitmap = (Bitmap) data.getExtras().get("data");
            //setBitmap(bitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteFormat = stream.toByteArray();
            // get the base 64 string
            String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
            new Upload(imgString);
            Toast.makeText(getApplication(), "PASSED", Toast.LENGTH_LONG).show();
            camera_icon.setImageBitmap(bitmap);

            //startActivity(new Intent(CategoryDetails.this, Summary.class));
            continue_btn.setVisibility(View.VISIBLE);
            continue_btn_flag = 1; //on
            session.set_encoded_string(imgString); //set

        }
    }

//    private String encodeImage(String path) {
//        Toast.makeText(CategoryDetails.this, "I in", Toast.LENGTH_LONG).show();
//
//        bitmap = BitmapFactory.decodeFile(path);
//        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//        //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
//        byte[] ba = bao.toByteArray();
//        encoded_string = Base64.encodeToString(ba, Base64.NO_WRAP);
//
//        Toast.makeText(CategoryDetails.this, "I OUT", Toast.LENGTH_LONG).show();
//        return encoded_string;
//    }

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
