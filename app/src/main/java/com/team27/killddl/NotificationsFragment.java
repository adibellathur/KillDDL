package com.team27.killddl;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.team27.killddl.data.DBHelper;
import com.team27.killddl.data.Task;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {


    View view;
    Context context;
    TextView dateDisplay1, dateDisplay2, dateDisplay3;
    ListView taskList1, taskList2, taskList3;
    DBHelper helper;
    ArrayAdapter<String> mAdapter1, mAdapter2, mAdapter3;
    Calendar today;
    String strToday, strTmw, strNext;
    Button btnDate;

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        context = container.getContext();
        helper = new DBHelper(view.getContext());
        taskList1 = view.findViewById(R.id.taskList1);
        taskList2 = view.findViewById(R.id.taskList2);
        taskList3 = view.findViewById(R.id.taskList3);
        dateDisplay3 = view.findViewById(R.id.dateDisplay3);

        today = Calendar.getInstance();
        strToday = DBHelper.getDateString(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        today.add(Calendar.DATE, 1);
        strTmw = DBHelper.getDateString(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        today.add(Calendar.DATE, 1);
        strNext = DBHelper.getDateString(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        setDate(today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.YEAR));

        refreshList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    public void refreshList() {
        loadTaskList1(strToday);
        loadTaskList2(strTmw);
        loadTaskList3(strNext);
    }

    private void loadTaskList1(String date) {
        ArrayList<Task> tasksComplete = helper.getTaskListByDate(date);
        ArrayList<String> tasks = new ArrayList<>();
        for(Task t : tasksComplete) {
            tasks.add(t.getName());
        }

        if(mAdapter1==null){
            mAdapter1 = new ArrayAdapter<>(view.getContext(),R.layout.row,R.id.list_taskName,tasks);
            taskList1.setAdapter(mAdapter1);
            mAdapter1.notifyDataSetChanged();
        }
        else{
            mAdapter1.clear();
            mAdapter1.addAll(tasks);
            mAdapter1.notifyDataSetChanged();
        }
    }

    private void loadTaskList2(String date) {
        ArrayList<Task> tasksComplete = helper.getTaskListByDate(date);
        ArrayList<String> tasks = new ArrayList<>();
        for(Task t : tasksComplete) {
            tasks.add(t.getName());
        }

        if(mAdapter2==null){
            mAdapter2 = new ArrayAdapter<>(view.getContext(),R.layout.row,R.id.list_taskName,tasks);
            taskList2.setAdapter(mAdapter2);
            mAdapter2.notifyDataSetChanged();
        }
        else{
            mAdapter2.clear();
            mAdapter2.addAll(tasks);
            mAdapter2.notifyDataSetChanged();
        }
    }

    private void loadTaskList3(String date) {
        ArrayList<Task> tasksComplete = helper.getTaskListByDate(date);
        ArrayList<String> tasks = new ArrayList<>();
        for(Task t : tasksComplete) {
            tasks.add(t.getName());
        }

        if(mAdapter3==null){
            mAdapter3 = new ArrayAdapter<>(view.getContext(),R.layout.row,R.id.list_taskName,tasks);
            taskList3.setAdapter(mAdapter3);
            mAdapter3.notifyDataSetChanged();
        }
        else{
            mAdapter3.clear();
            mAdapter3.addAll(tasks);
            mAdapter3.notifyDataSetChanged();
        }
    }

    private void setDate(int monthOfYear, int dayOfMonth, int year) {
        dateDisplay3.setText((monthOfYear + 1) + "/"
                + dayOfMonth + "/" + year);
    }

}
