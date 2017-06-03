package me.bobaikato.app.report;
/**
 * Author: Bobai Kato
 * Date: 6/2/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static me.bobaikato.app.report.Permissions.checkNetwork;

public class Login extends AppCompatActivity {
    EditText password, username;
    TextView signup_msg, login, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Font*/
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font_1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");

        login = (TextView) findViewById(R.id.user_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signup_msg = (TextView) findViewById(R.id.user_signup);
        signup = (TextView) findViewById(R.id.signup);

        login.setTypeface(custom_font_1);
        signup_msg.setTypeface(custom_font);
        username.setTypeface(custom_font);
        password.setTypeface(custom_font);
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
        checkNetwork(findViewById(android.R.id.content), Login.this);
    }


    /*handles Login validation and redirection to Category*/
    public void userLogin(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        /*Delays*/
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        Intent intent = new Intent(Login.this, Category.class);
                        startActivity(intent);
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
}
