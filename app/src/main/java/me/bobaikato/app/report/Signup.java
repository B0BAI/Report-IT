package me.bobaikato.app.report;

/*
 * Author: Bobai Kato
 * Date: 6/2/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static me.bobaikato.app.report.Permissions.checkNetwork;

public class Signup extends AppCompatActivity {
    private static final String URL = "https://www.report.lastdaysmusic.com/user/signup.php";
    EditText email, ppsnumber, password, username;
    TextView login_msg, signup, login;
    ArrayList<String> msg = new ArrayList<>();
    private String email_val, ppsnumber_val, password_val, username_val;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

                /*Font*/
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font_1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");

        /*IDs*/
        login = (TextView) findViewById(R.id.login);
        signup = (TextView) findViewById(R.id.user_signup);
        login_msg = (TextView) findViewById(R.id.user_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email_add);
        ppsnumber = (EditText) findViewById(R.id.ppsno);


        ppsnumber.setTypeface(custom_font);
        signup.setTypeface(custom_font_1);
        password.setTypeface(custom_font);
        login_msg.setTypeface(custom_font);
        username.setTypeface(custom_font);
        email.setTypeface(custom_font);

          /*Logging text click listener*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Signup.this, Login.class);
                startActivity(it);
            }
        });

        /*Signup Onclick listener*/
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Values*/
                username_val = username.getText().toString().trim();
                password_val = password.getText().toString();
                email_val = email.getText().toString().trim();
                ppsnumber_val = ppsnumber.getText().toString().trim();

                //Toast.makeText(getApplicationContext(), "SIGNUP Clicked", Toast.LENGTH_SHORT).show();
                if (email_val.isEmpty() || email_val.length() == 0 || email_val.equals("") || email_val == null) {
                    Snackbar.make(v, "Email is invalid ", Snackbar.LENGTH_LONG).show();
                } else if (username_val.isEmpty() || username_val.length() == 0 || username_val.equals("") || username_val == null) {
                    Snackbar.make(v, "Username is invalid", Snackbar.LENGTH_LONG).show();
                } else if (password_val.isEmpty() || password_val.length() == 0 || password_val.equals("") || password_val == null) {
                    Snackbar.make(v, "Password is invalid", Snackbar.LENGTH_LONG).show();
                } else if (ppsnumber_val.isEmpty() || ppsnumber_val.length() == 0 || ppsnumber_val.equals("") || ppsnumber_val == null) {
                    Snackbar.make(v, "PPS Number is invalid", Snackbar.LENGTH_LONG).show();
                } else {
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
        });
    }

    private class Action extends AsyncTask {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*Reset Fields*/
            email.setText("");
            username.setText("");
            password.setText("");
            ppsnumber.setText("");
            /**
             * Progress Dialog for User Interaction
             */
            progressDialog = new ProgressDialog(Signup.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
            Toast.makeText(Signup.this, msg.get(0), Toast.LENGTH_LONG).show();
               /*Delays*/
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            startActivity(new Intent(Signup.this, Login.class));
                        }
                    }, 2000);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String success = null, warning = null, failed = null;
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("email", email_val)
                    .add("username", username_val)
                    .add("password", password_val)
                    .add("ppsno", ppsnumber_val)
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .post(formBody)
                    .build();
            Response responses = null;

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    msg.add("Failed");
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
                    String success = null;
                    try {
                        success = json.getString("message").trim();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    msg.add(success);
                }
            });
            return null;
        }
    }
}
