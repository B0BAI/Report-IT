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

public class Signup extends AppCompatActivity {
    EditText email, phoneno, password, username;
    TextView login_msg, signup, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        login = (TextView) findViewById(R.id.login);
        signup = (TextView) findViewById(R.id.user_signup);
        login_msg = (TextView) findViewById(R.id.user_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        phoneno = (EditText) findViewById(R.id.mobphone);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        phoneno.setTypeface(custom_font);
        signup.setTypeface(custom_font1);
        password.setTypeface(custom_font);
        login_msg.setTypeface(custom_font);
        username.setTypeface(custom_font);
        email.setTypeface(custom_font);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Signup.this, Login.class);
                startActivity(it);
            }
        });
    }
}