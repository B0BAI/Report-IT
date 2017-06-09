package me.bobaikato.app.report;
/**
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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Signup extends AppCompatActivity {
    EditText email, ppsnumber, password, username;
    TextView login_msg, signup, login;
    private String email_val, ppsnumber_val, password_val, username_val;

    OkHttpClient client = new OkHttpClient();
    private static final String URL = "https://www.report.lastdaysmusic.com/user/signup.php";
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

                    progressDialog = new ProgressDialog(Signup.this,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();

                    new Action().execute();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    class Action extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
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
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    progressDialog.dismiss();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //progressDialog.dismiss();
                    try {
                        String resp = response.body().string();
//                    Log.v(TAG_REGISTER, resp);
                        System.out.println(resp);
                        if (response.isSuccessful()) {
                        } else {

                        }
                    } catch (IOException e) {
                        // Log.e(TAG_REGISTER, "Exception caught: ", e);
                        System.out.println("Exception caught" + e.getMessage());
                    }
                }
            });

            return null;
        }
    }
}