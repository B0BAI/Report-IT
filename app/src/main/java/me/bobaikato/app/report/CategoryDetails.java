package me.bobaikato.app.report;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CategoryDetails extends AppCompatActivity {

    private static final String URL = "https://www.report.lastdaysmusic.com/report/upload.php";
    private static Integer view_id;
    String picturePath;
    private Fonts fonts;
    private ImageView image;
    private TextView title, test_btn, details;
    private LocationManager manager;
    private String encoded_string, image_name = "myimageszzzz.jpg";
    private Bitmap bitmap;
    private Uri file_uri;

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
        test_btn = (TextView) findViewById(R.id.testbtn);
        details = (TextView) findViewById(R.id.report_details);
        image = (ImageView) findViewById(R.id.upload_camera);
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


        image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkGPS()) {
                    if (getApplicationContext().getPackageManager().hasSystemFeature(
                            PackageManager.FEATURE_CAMERA)) {
                        // Open default camera
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);

                        // start the image capture Intent
                        startActivityForResult(intent, 100);

                    } else {
                        Toast.makeText(getApplication(), R.string.cam_support_msg, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Action().execute();
                Toast.makeText(getApplication(), "DONE UPLOADING", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {

            file_uri = data.getData();
            bitmap = (Bitmap) data.getExtras().get("data");

            // Cursor to get image uri to display

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(file_uri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            bitmap = (Bitmap) data.getExtras().get("data");

            //image.setImageBitmap(bitmap);

            encoded_string = encodeImage(picturePath);
            Toast.makeText(CategoryDetails.this, picturePath, Toast.LENGTH_LONG).show();
        }
    }

    private String encodeImage(String path) {
        Toast.makeText(CategoryDetails.this, "I in", Toast.LENGTH_LONG).show();

        bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte[] ba = bao.toByteArray();
        encoded_string = Base64.encodeToString(ba, Base64.NO_WRAP);

        Toast.makeText(CategoryDetails.this, "I OUT", Toast.LENGTH_LONG).show();
        return encoded_string;
    }

    private void upload() {
        // Image location URL
        Log.e("path", "----------------" + picturePath);

        // Image
        bitmap = BitmapFactory.decodeFile(picturePath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bao);
        byte[] ba = bao.toByteArray();
        encoded_string = Base64.encodeToString(ba, Base64.NO_WRAP);

        Log.e("base64", "-----" + encoded_string);
        Toast.makeText(CategoryDetails.this, encoded_string, Toast.LENGTH_LONG).show();
        // Upload image to server
        // new Action().execute();

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

    /*AsyncTask*/
    private class Action extends AsyncTask {


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Toast.makeText(CategoryDetails.this, "DONE!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Object doInBackground(Object[] params) {


//            bitmap = BitmapFactory.decodeFile(file_uri.getPath());
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            bitmap.recycle();
//
//
//
//            byte[] array = stream.toByteArray();
//            encoded_string = Base64.encodeToString(array, 0);


            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("encoded_string", encoded_string)
                    .add("image_name", image_name)
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //requestResponse = "0";
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String resStr = response.body().string();
                    JSONObject json = null;
                    try {
                        json = new JSONObject(resStr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json.getString("").trim();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }
    }
}
