package com.team27.killddl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.team27.killddl.data.LoginDBHelper;

public class SignUpActivity extends AppCompatActivity {
    private EditText name;
    private EditText username;
    private EditText password;
    private Button signup;
    private LoginDBHelper loginDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loginDB = new LoginDBHelper(this);
        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        signup = (Button) findViewById(R.id.SignUpBtn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nm = name.getText().toString();
                String us = username.getText().toString();
                String pw = password.getText().toString();
                //showToast(us);
                if(loginDB.insertNewUser(nm, us, pw)){
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    TextView error = (TextView) findViewById(R.id.error);
                    error.setText("User already exists.");
                }



            }
        });
    }

    private void showToast(String content) {
        Toast toast;
        toast = Toast.makeText(SignUpActivity.this, content, Toast.LENGTH_LONG);
        toast.setMargin(0, (float)0.07);
        toast.show();
    }

}
