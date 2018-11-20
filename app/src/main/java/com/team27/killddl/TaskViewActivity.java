package com.team27.killddl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.team27.killddl.data.DBHelper;

import com.team27.killddl.data.DBHelper;
import com.team27.killddl.data.Task;
import com.team27.killddl.data.TaskContract;

import org.w3c.dom.Text;

public class TaskViewActivity extends AppCompatActivity {
    private DBHelper helper;
    private TextView taskName;
    private TextView taskDescription;
    private TextView taskDate;
    private TextView taskPriority;
    private Button deleteButton;
    public String tname;

    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Dark = "darkKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (!prefs.contains(Dark)) {
            prefs.edit().putBoolean(Dark, false);
        }
        if (prefs.getBoolean(Dark, true)) {
            setTheme(R.style.Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        helper = new DBHelper(this);
        taskName = (TextView) findViewById(R.id.textView_taskName);
        taskPriority = (TextView) findViewById(R.id.textView_taskPriority);
        taskDate = (TextView) findViewById(R.id.textView_taskDate);
        taskDescription = (TextView) findViewById(R.id.textView_taskDescription);
        deleteButton = (Button) findViewById(R.id.button_delete);

        String name= getIntent().getStringExtra("NAME"); //from DayCalandarFragment intent
        showToast(name);
        Task t = helper.getTask(name);
        String date = t.getDate();
        showToast(date);
        tname = t.getName();
        String description = t.getDescription();
        showToast(description);
        int priority = t.getPriority();
        if(priority == 0){
            taskPriority.setTextColor(Color.BLUE);
        }
        else if(priority == 1){
            taskPriority.setTextColor(Color.GREEN);
        }
        else if(priority == 2){
            taskPriority.setTextColor(Color.YELLOW);
        }
        else if(priority == 3){
            taskPriority.setTextColor(Color.parseColor("#FFA500"));
        }
        else if(priority == 4){
            taskPriority.setTextColor(Color.RED);
        }

        taskName.setText(tname);
        taskPriority.setText("Priority: " + Integer.toString(priority));
        taskDate.setText("Due Date: " + date);
        taskDescription.setText("Description: \n" + description);

    }
    private void showToast(String content) {
        Toast toast;
        toast = Toast.makeText(TaskViewActivity.this, content, Toast.LENGTH_SHORT);
        toast.setMargin(0, (float)0.07);
        toast.show();
    }

    public void deleteTask(View view){
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.textView_taskName);
        String task = String.valueOf(taskTextView.getText());
        showToast("deleting " + task);
        helper.deleteTask(task);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void editTask(View view){


        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra("NAME", tname);
        showToast(tname);
        startActivity(intent);
    }
}


