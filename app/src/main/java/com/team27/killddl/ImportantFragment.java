package com.team27.killddl;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.team27.killddl.data.DBHelper;
import com.team27.killddl.data.Task;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImportantFragment extends Fragment {


    View view;
    Context context;
    ListView taskListPriority;
    DBHelper helper;
    ArrayAdapter<String> mAdapter;

    private ViewPager viewPager;
    private ViewPageAdapter adapter;

    public ImportantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_important, container, false);
        context = container.getContext();
        helper = new DBHelper(view.getContext());
        taskListPriority = view.findViewById(R.id.taskListPriority);

        adapter = new ViewPageAdapter(getChildFragmentManager());
        setRetainInstance(true);

        taskListPriority.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String value = (String)parent.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), TaskViewActivity.class);
                intent.putExtra("NAME", value);
                startActivity(intent);
            }
        });

        loadTaskList();
        refreshList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    public void refreshList() {
        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<Task> tasksComplete = helper.getTaskListByPriority();
        ArrayList<String> tasks = new ArrayList<>();
        String output;
        for(Task t : tasksComplete) {
            output = t.getName();
            tasks.add(output);
        }

        //if(mAdapter==null){
            mAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.row_simple,R.id.list_taskName_Simple,tasks);
            taskListPriority.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        /*
        }
        else{
            mAdapter.clear();
            mAdapter.addAll(tasks);
            mAdapter.notifyDataSetChanged();
        }
        */
    }

}
