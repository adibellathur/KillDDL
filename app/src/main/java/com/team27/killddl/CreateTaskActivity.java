package com.team27.killddl;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class CreateTaskActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        toolbar = getSupportActionBar();
        toolbar.setElevation(0);
        toolbar.setTitle("Create a New Task");
    }

}
