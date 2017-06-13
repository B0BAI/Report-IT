package me.bobaikato.app.report;

/*
 * Author: Bobai Kato
 * Date: 6/2/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 */

import android.app.ProgressDialog;
import android.content.Intent;
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
    private EditText email, ppsnumber, password, username;
    private TextView login_msg, signup, login;
    private String requestResponse;
    private String email_val, ppsnumber_val, password_val, username_val;
    private ProgressDialog progressDialog;
    private Fonts fonts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                /*Font*/
        fonts = new Fonts(getApplicationContext());

        /*IDs*/
        login = (TextView) findViewById(R.id.login);
        signup = (TextView) findViewById(R.id.user_signup);
        login_msg = (TextView) findViewById(R.id.user_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email_add);
        ppsnumber = (EditText) findViewById(R.id.ppsno);


        ppsnumber.setTypeface(fonts.getCustom_font());
        signup.setTypeface(fonts.getCustom_font_1());
        password.setTypeface(fonts.getCustom_font());
        login_msg.setTypeface(fonts.getCustom_font());
        username.setTypeface(fonts.getCustom_font());
        email.setTypeface(fonts.getCustom_font());

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

            /*
             * Progress Dialog for User Interaction
             */
            progressDialog = new ProgressDialog(Signup.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getString(R.string.authenticating));
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String user_msg = null;
            // Toast.makeText(Signup.this,"H E R E",Toast.LENGTH_SHORT).show();
            if (requestResponse.equals("1")) {
                  /*Reset Fields*/
                email.setText("");
                username.setText("");
                password.setText("");
                ppsnumber.setText("");
                user_msg = getString(R.string.create_acc_success_msg);
            } else if (requestResponse.equals("0")) {
                user_msg = getString(R.string.acct_create_fail_msg);
            } else if (requestResponse.equals("2")) {
                user_msg = getString(R.string.accnt_create_error_msg);
            }

            progressDialog.dismiss();
            Toast.makeText(Signup.this, user_msg, Toast.LENGTH_SHORT).show();
               /*Delays*/
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            if (requestResponse.equals("1")) {
                                startActivity(new Intent(Signup.this, Login.class));
                            } else {
                                Toast.makeText(Signup.this, "REG. FAILED", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 1000);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("email", email_val.toLowerCase())
                    .add("username", username_val.toLowerCase())
                    .add("password", password_val.toLowerCase())
                    .add("ppsno", ppsnumber_val.toLowerCase())
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String resStr = response.body().string();
                    JSONObject json = null;
                    try {
                        json = new JSONObject(resStr);
                        requestResponse = json.getString("message").trim();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            return null;
        }
    }
}
