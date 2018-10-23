package com.team27.killddl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Calendar;

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
                //showToast("Priority:" + getPriority());

                int day  = taskDueDate.getDayOfMonth();
                int month= taskDueDate.getMonth();
                int year = taskDueDate.getYear();
                String dateString = DBHelper.getDateString(year, month, day);

                // showToast("Date:" + dateString);

                SaveTask(taskName.getText().toString(),
                        taskDescription.getText().toString(),
                        getPriority(),
                        dateString);

                // Sets notification for noon the day before a task is due
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent notificationIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day-1, 12, 0, 0);
                showToast("Notification Time:" + cal.getTime());
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
            }
        });

    }

    private void SaveTask(String name, String description, int priority, String date) {
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
