package com.team27.killddl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.team27.killddl.data.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private DBHelper helper;

    private EditText taskName;
    private EditText taskDescription;
    private DatePicker taskDueDate;
    private RadioGroup taskPriority;
    private Spinner frequency;
    private EditText numRecurs;


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

        frequency = (Spinner) findViewById(R.id.spinner1);
        numRecurs = (EditText) findViewById(R.id.numRecurrences);
        //numRecurs.setText(0);
        String[] items = new String[]{"Only once", "Daily", "Weekly", "Monthly", "Yearly"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        frequency.setAdapter(adapter);

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
                //showToast("Year: " + year + "Month: " + month + "Day: " + day);
                String d = Integer.toString(day);
                if(day < 10) d = "0" + d;
                String m = Integer.toString(month+1);
                if(month < 10) m = "0" + m;
                String y = Integer.toString(year);
                String dt = y + "-" + m + "-" + d;  // Start date
                showToast(dt);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(dt));
                }catch(ParseException pe){
                    showToast("failed to parse!");
                }

                //showToast("incremented: " + dt);
                String freq = frequency.getSelectedItem().toString();
                //showToast("frequency" + freq);

                 showToast("Date:" + dateString);
                if(freq.equals("Only once")){
                    SaveTask(taskName.getText().toString(),
                            taskDescription.getText().toString(),
                            getPriority(),
                            dateString);
                }
               else{
                    int numR = Integer.parseInt(numRecurs.getText().toString());
                    int amount;
                    for(int i = 0; i < numR; ++i) {
                        String[] parts = dt.split("-");
                        dateString = DBHelper.getDateString(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) - 1,
                                Integer.parseInt(parts[2]));
                        SaveTask(taskName.getText().toString(),
                                taskDescription.getText().toString(),
                                getPriority(),
                                dateString);
                        if(freq.equals("Daily"))
                            c.add(Calendar.DATE, 1);
                        else if(freq.equals("Weekly"))
                            c.add(Calendar.DATE, 7);
                        else if(freq.equals("Monthly"))
                            c.add(Calendar.MONTH, 1);
                        else if(freq.equals("Yearly")){
                            c.add(Calendar.YEAR, 1);
                        }
                        dt = sdf.format(c.getTime());
                    }

                }


                // Sets notification for noon the day before a task is due
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent notificationIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day-1, 12, 0, 0);
                //showToast("Notification Time:" + cal.getTime());
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
