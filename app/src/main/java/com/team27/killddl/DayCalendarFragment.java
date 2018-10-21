package com.team27.killddl;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.team27.killddl.R;
import com.team27.killddl.data.DBHelper;
import com.team27.killddl.data.Task;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayCalendarFragment extends Fragment {

    View view;
    ListView taskList;
    DBHelper helper;
    ArrayAdapter<String> mAdapter;

    public DayCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_day_calendar, container, false);

        helper = new DBHelper(view.getContext());
        taskList = (ListView) view.findViewById(R.id.taskList);
        loadTaskList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<Task> tasksComplete = helper.getTaskList();
        ArrayList<String> tasks = new ArrayList<>();
        for(Task t : tasksComplete) {
            tasks.add(t.getName());
        }

        if(mAdapter==null){
            mAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.row,R.id.list_taskName,tasks);
            taskList.setAdapter(mAdapter);
        }
        else{
            mAdapter.clear();
            mAdapter.addAll(tasks);
            mAdapter.notifyDataSetChanged();
        }
    }

}
