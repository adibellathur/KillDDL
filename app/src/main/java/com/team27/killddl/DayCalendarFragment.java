package com.team27.killddl;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.team27.killddl.data.DBHelper;
import com.team27.killddl.data.Task;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayCalendarFragment extends Fragment {

    View view;
    Context context;
    TextView dateDisplay;
    ListView taskList;
    DBHelper helper;
    ArrayAdapter<String> mAdapter;
    Calendar today;
    Button btnDate;
    Dialog dialog;
    private int mYear, mMonth, mDay;

    static final int DATE_DIALOG_ID = 0;

    public DayCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_day_calendar, container, false);
        context = container.getContext();
        helper = new DBHelper(view.getContext());
        dateDisplay = (TextView) view.findViewById(R.id.dateDisplay);
        taskList = (ListView) view.findViewById(R.id.taskList);
        btnDate = (Button) view.findViewById(R.id.btnDate);
        today = Calendar.getInstance();
        String date = DBHelper.getDateString(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));

        loadTaskList(date);
        setDate(today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.YEAR));

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Process to get Current Date
                mYear = today.get(Calendar.YEAR);
                mMonth = today.get(Calendar.MONTH);
                mDay = today.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                setDate(monthOfYear, dayOfMonth, year);
                                String date = DBHelper.getDateString(year, monthOfYear, dayOfMonth);
                                loadTaskList(date);
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //String date = DBHelper.getDateString(today.get(Calendar.YEAR), today.get(Calendar.MONTH + 1), today.get(Calendar.DAY_OF_MONTH));
        //loadTaskList(date);
    }

    private void loadTaskList(String date) {
        ArrayList<Task> tasksComplete = helper.getTaskListByDate(date);
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

    private void setDate(int monthOfYear, int dayOfMonth, int year) {
        dateDisplay.setText((monthOfYear + 1) + "/"
                + dayOfMonth + "/" + year);
    }


}
