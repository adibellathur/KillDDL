package com.team27.killddl;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.team27.killddl.data.DBHelper;
import com.team27.killddl.data.TaskContract;

import java.sql.Time;
import java.util.Timer;

public class CreateTaskActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private DBHelper helper;

    private EditText taskName;
    private EditText taskDescription;
    private DatePicker taskDueDate;
    private RadioGroup taskPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        helper = new DBHelper(this);
        toolbar = getSupportActionBar();
        toolbar.setElevation(0);
        toolbar.setTitle("Create a New Task");

        taskName = (EditText) findViewById(R.id.taskName);
        taskDescription = (EditText) findViewById(R.id.taskDescription);
        taskDueDate = (DatePicker) findViewById(R.id.taskDate);
        taskPriority = (RadioGroup) findViewById(R.id.taskPriority);


        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                // Debugging
                // view.offsetTopAndBottom(-50);
                showToast("priority:" + getPriority());

                SaveTask(taskName.getText().toString(),
                        taskDescription.getText().toString(),
                        getPriority(),
                        0);
            }
        });

    }

    private void SaveTask(String name, String description, int priority, int date) {
        helper.insertNewTask(name, description, priority, date);
        finish();
    }

    private int getPriority() {
        int id = taskPriority.getCheckedRadioButtonId();
        View radioButton = taskPriority.findViewById(id);
        int idx = taskPriority.indexOfChild(radioButton);
        RadioButton r = (RadioButton) taskPriority.getChildAt(idx);
        String value = r.getText().toString();
        return Integer.parseInt(value);
    }

    private void showToast(String content) {
        Toast toast;
        toast = Toast.makeText(CreateTaskActivity.this, content, Toast.LENGTH_SHORT);
        toast.setMargin(0, (float)0.07);
        toast.show();
    }

}
