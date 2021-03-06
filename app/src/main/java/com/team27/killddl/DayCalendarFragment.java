package com.team27.killddl;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    DynamicListView taskList;
    DBHelper helper;
    StableArrayAdapter mAdapter;
    Calendar today;
    Button btnDate;
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
        taskList = (DynamicListView) view.findViewById(R.id.taskList);

        btnDate = (Button) view.findViewById(R.id.btnDate);
        today = Calendar.getInstance();
        String date = DBHelper.getDateString(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String value = (String)parent.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), TaskViewActivity.class);
                intent.putExtra("NAME", value);
                startActivity(intent);
            }
        });

        loadTaskList(date);
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
        today = Calendar.getInstance();
        String date = DBHelper.getDateString(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        loadTaskList(date);
        setDate(today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.YEAR));
    }


    private void loadTaskList(String date) {
        ArrayList<Task> tasksComplete = helper.getTaskListByDate(date);
        ArrayList<String> tasks = new ArrayList<>();
        String output;
        for (Task t : tasksComplete) {

            if(t.isComplete() == 0){
                output = t.getName();
                tasks.add(output);
            }

        }

        if (mAdapter == null) {
            mAdapter = new StableArrayAdapter(view.getContext(), R.layout.text_view, tasks);
            taskList.setCheeseList(tasks);
            taskList.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter = new StableArrayAdapter(view.getContext(), R.layout.text_view, tasks);
            taskList.setCheeseList(tasks);
            taskList.setAdapter(mAdapter);
        }
        taskList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mAdapter.notifyDataSetChanged();

    }

    private void setDate(int monthOfYear, int dayOfMonth, int year) {
        dateDisplay.setText("Tasks Today (" + (monthOfYear + 1) + "/"
                + dayOfMonth + "/" + year + ")");
    }
    private void showToast(String content) {
        Toast toast = Toast.makeText(this.getContext(), content, Toast.LENGTH_SHORT);
        toast.setMargin(0, (float)0.07);
        toast.show();
    }

}
