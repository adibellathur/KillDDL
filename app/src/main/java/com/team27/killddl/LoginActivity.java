package com.team27.killddl;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final String EMAIL = "email";

    //LoginActivity
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView skipText;
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //check if user is already logged in (through facebook)
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn == true){
            login(accessToken.getUserId());
        }

        //skip log in, default user?
        skipText = (TextView) findViewById(R.id.skipText);
        skipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if a user clicks skip, login locally
                login("default");
            }
        });

        //Facebook LoginActivity
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());
        login = findViewById(R.id.btnSubmit);
        loginButton = findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userID = loginResult.getAccessToken().getUserId();
                login(userID);
            }

            @Override
            public void onCancel() {
                showToast("Cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                showToast("error " + error.toString());
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(next);

            }
        });
    }


    private void showToast(String content) {
        Toast toast;
        toast = Toast.makeText(LoginActivity.this, content, Toast.LENGTH_LONG);
        toast.setMargin(0, (float)0.07);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void login(String user){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        intent.putExtra("userID", user);
        startActivity(intent);
    }
}
