package com.team27.killddl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.team27.killddl.data.DBHelper;

import com.team27.killddl.data.DBHelper;
import com.team27.killddl.data.Task;
import com.team27.killddl.data.TaskContract;

public class TaskViewActivity extends AppCompatActivity {
    private DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        helper = new DBHelper(this);

        String name= getIntent().getStringExtra("NAME"); //from DayCalandarFragment intent
        showToast(name);
        Task t = helper.getTask(name);
        String date = t.getDate();
        showToast(date);
        //String name = t.getName();
        String description = t.getDescription();
        showToast(description);




    }
    private void showToast(String content) {
        Toast toast;
        toast = Toast.makeText(TaskViewActivity.this, content, Toast.LENGTH_SHORT);
        toast.setMargin(0, (float)0.07);
        toast.show();
    }
}


