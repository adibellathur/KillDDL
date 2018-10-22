package com.team27.killddl;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.team27.killddl.data.DBHelper;
import com.team27.killddl.data.Task;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonthCalendarFragment extends Fragment {

    View view;
    Context context;
    DBHelper helper;
    TextView monthDateDisplay;
    Calendar today;

    ArrayAdapter<String> mAdapter;
    ListView monthTaskList;

    public MonthCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_month_calendar, container, false);
        context = container.getContext();
        helper = new DBHelper(view.getContext());
        monthTaskList = view.findViewById(R.id.monthTaskList);
        monthDateDisplay = view.findViewById(R.id.monthDateDisplay);


        today = Calendar.getInstance();
        String date = DBHelper.getDateString(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        loadTaskList(date);
        setDate(today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.YEAR));

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int monthOfYear,
                                            int dayOfMonth) {
                setDate(monthOfYear, dayOfMonth, year);
                String date = DBHelper.getDateString(year, monthOfYear, dayOfMonth);
                loadTaskList(date);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    public void refreshList() {
        today = Calendar.getInstance();
        String date = DBHelper.getDateString(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        loadTaskList(date);
        setDate(today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.YEAR));
    }

    private void loadTaskList(String date) {
        ArrayList<Task> tasksComplete = helper.getTaskListByDate(date);
        ArrayList<String> tasks = new ArrayList<>();
        for(Task t : tasksComplete) {
            tasks.add(t.getName());
        }

        if(mAdapter==null){
            mAdapter = new ArrayAdapter<>(view.getContext(),R.layout.row,R.id.list_taskName,tasks);
            monthTaskList.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        else{
            mAdapter.clear();
            mAdapter.addAll(tasks);
            mAdapter.notifyDataSetChanged();
        }
    }


    private void setDate(int monthOfYear, int dayOfMonth, int year) {
        monthDateDisplay.setText((monthOfYear + 1) + "/"
                + dayOfMonth + "/" + year);
    }

}
