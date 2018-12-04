package com.team27.killddl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.team27.killddl.data.DBHelper;
import com.team27.killddl.data.Task;

import java.util.ArrayList;


public class CompletedTaskView extends AppCompatActivity {
    private TextView numCompleted;
    private ListView completedTasks;
    private View view;
    private DBHelper helper;
    private ArrayAdapter<String> mAdapter;

    String nCompleted;

    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Dark = "darkKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (prefs.getBoolean(Dark, true)) {
            setTheme(R.style.Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task_view);

        helper = new DBHelper(this);

        completedTasks = (ListView) findViewById(R.id.completedTaskList);
        numCompleted = (TextView) findViewById(R.id.numComplete);

        ArrayList<Task> tasks = helper.getTaskList();
        nCompleted = String.valueOf(tasks.size());
        if(tasks.size() == 0){
            numCompleted.setText("You haven't completed any tasks. Keep Working! \n");
        }
        else {
            numCompleted.setText("You have completed " + tasks.size() + " tasks. Great Work! \n");
        }

        completedTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String value = (String)parent.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), TaskViewActivity.class);
                intent.putExtra("NAME", value);
                startActivity(intent);
            }
        });


        refreshList();
        loadTaskList();
    }


    public void refreshList() {
        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<Task> tasksComplete = helper.getTaskList();
        ArrayList<String> tasks = new ArrayList<>();
        String output;
        for(Task t : tasksComplete) {
            if(t.isComplete() == 1) {
                output = t.getName();
                tasks.add(output);
            }
        }

       if(mAdapter==null){
            mAdapter = new ArrayAdapter<String>(this,R.layout.row_simple,R.id.list_taskName_Simple,tasks);
            completedTasks.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }
        else{
            mAdapter.clear();
            mAdapter.addAll(tasks);
            mAdapter.notifyDataSetChanged();
        }

    }

    public void shareProgress(View view) {
        String msg = "I have completed " + nCompleted + " tasks using KillDDL. Get the app here: https://play.google.com/store";

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}


