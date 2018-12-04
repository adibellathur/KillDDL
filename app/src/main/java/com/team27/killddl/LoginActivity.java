package com.team27.killddl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.team27.killddl.data.LoginDBHelper;

import org.w3c.dom.Text;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final String EMAIL = "email";

    //LoginActivity
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView skipText;
    private Button login;
    private EditText username;
    private EditText password;
    private LoginDBHelper loginDB;
    private Button signup;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Dark = "darkKey";
    public static final String Notification = "notificationKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginDB = new LoginDBHelper(this);
        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (!prefs.contains(Dark)) {
            prefs.edit().putBoolean(Dark, false).commit();
            prefs.edit().putBoolean(Notification, true).commit();
        }
        if (prefs.getBoolean(Dark, true)) {
            setTheme(R.style.Dark);
        }

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
        //loginDB.deleteAllUsers();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginDB.insertNewUser("Teyva", "teyva", "abc");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String us = username.getText().toString();
                String pw = password.getText().toString();
                //showToast(us);
                String result = loginDB.verifyUser(us, pw);
                //showToast(result);
                if(result.equals("valid")) {
                    Intent next = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(next);
                }
                else if(result.equals("invalid")){
                    TextView res = (TextView) findViewById(R.id.result);
                    res.setText("Incorrect Password");
                    //showToast("Invalid Password");
                }
                else{
                    TextView res = (TextView) findViewById(R.id.result);
                    res.setText("User does not exist.");
                    //showToast("User does not exist.");
                }

            }
        });

        signup = (Button) findViewById(R.id.SignUpBtn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
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
