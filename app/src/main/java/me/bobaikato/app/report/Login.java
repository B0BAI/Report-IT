package me.bobaikato.app.report;
/**
 * Author: Bobai Kato
 * Date: 6/2/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 */

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
         /*Check InternetConnection Connection*/
        new InternetConnection(findViewById(android.R.id.content), Login.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
         /*Check InternetConnection Connection*/
        new InternetConnection(findViewById(android.R.id.content), Login.this);
    }


    public void userLogin(View view){
        Intent intent  = new Intent(this, Home.class);
        startActivity(intent);
    }
}
