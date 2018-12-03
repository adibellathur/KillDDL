package com.team27.killddl;

import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task_view);

        helper = new DBHelper(this);

        completedTasks = (ListView) findViewById(R.id.completedTaskList);
        numCompleted = (TextView) findViewById(R.id.numComplete);

        ArrayList<Task> tasks = helper.getTaskList();
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
}


