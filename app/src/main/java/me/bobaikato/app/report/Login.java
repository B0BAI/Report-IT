package me.bobaikato.app.report;
/**
 * Author: Bobai Kato
 * Date: 6/2/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 */

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static me.bobaikato.app.report.Permissions.checkLocation;
import static me.bobaikato.app.report.Permissions.checkNetwork;

public class Login extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String URL = "https://www.report.lastdaysmusic.com/user/login.php";
    public static Session session;
    private EditText password, username;
    private TextView signup_msg, login, signup;
    private ProgressDialog progressDialog;
    private String username_val;
    private String password_val;
    private Fonts fonts;
    private JSONArray jsonarray;
    private String ppsno_resp = null;
    private String type_resp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        session = new Session(getApplicationContext());


        handleSession();

        /*Check Location approval*/
        if (!checkLocation(getApplicationContext())) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

        /*Font*/
        fonts = new Fonts(getApplicationContext());

        login = (TextView) findViewById(R.id.user_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signup_msg = (TextView) findViewById(R.id.user_signup);
        signup = (TextView) findViewById(R.id.signup);

        login.setTypeface(fonts.getCustom_font_1());
        signup_msg.setTypeface(fonts.getCustom_font());
        username.setTypeface(fonts.getCustom_font());
        password.setTypeface(fonts.getCustom_font());
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this, Signup.class);
                startActivity(it);
            }
        });
         /*Check Permissions Connection*/
        checkNetwork(findViewById(android.R.id.content), Login.this);
    }


    @Override
    protected void onResume() {
        super.onResume();
         /*Check Permissions Connection*/
        handleSession();
        checkNetwork(findViewById(android.R.id.content), Login.this);
    }

    /*Handle session Redirect*/
    private void handleSession() {
        /*Check Session and redirect to Category*/
        if (session.isIdentity()) {
            startActivity(new Intent(Login.this, Category.class));
        }
    }

    /*handles Login validation and redirection to Category*/
    public void userLogin(View view) {
        username_val = username.getText().toString().trim();
        password_val = password.getText().toString();

        if (username_val.isEmpty() || username_val.length() == 0) {
            Snackbar.make(view, "Username cannot be empty.", Snackbar.LENGTH_LONG).show();
        } else if (password_val.isEmpty() || password_val.length() == 0) {
            Snackbar.make(view, "Password cannot be empty.", Snackbar.LENGTH_LONG).show();
        } else {

        /*Dialog*/
            if (checkNetwork(findViewById(android.R.id.content), getApplicationContext())) {
                new Action().execute();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class Action extends AsyncTask {
        ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*
             * Progress Dialog for User Interaction
             */
            progressDialog = new ProgressDialog(Login.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getString(R.string.authenticating));
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
                   /*Delays*/
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            if (!ppsno_resp.equals(getString(R.string.invalid))) {
                                /*Set pref*/
                                session.setIdentity(ppsno_resp);
                                /*Reset Field*/
                                username.setText("");
                                password.setText("");
                                startActivity(new Intent(Login.this, Category.class));
                                Toast.makeText(Login.this, R.string.login_success_msg, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Login.this, R.string.login_failed_msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 1000);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("username", username_val)
                    .add("password", password_val)
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //requestResponse = "invalid";
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    JSONObject ppsno_JSON, user_Type_JSON;
                    String resStr = response.body().string();
                    try {
                        jsonarray = new JSONArray(resStr);
                        ppsno_JSON = jsonarray.getJSONObject(0);
                        user_Type_JSON = jsonarray.getJSONObject(0);
                        type_resp = user_Type_JSON.getString("type").toString();
                        ppsno_resp = ppsno_JSON.getString("ppsno").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }
    }

}
