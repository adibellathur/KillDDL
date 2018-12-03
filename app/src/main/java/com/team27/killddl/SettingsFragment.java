package com.team27.killddl;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.team27.killddl.R;
import com.team27.killddl.data.DBHelper;
import com.team27.killddl.data.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class  SettingsFragment extends Fragment {

    private View view;
    private Button loginLogout;
    private Switch toggleDark;
    private Switch toggleNotifs;
    private Button reset;
    private Button Close;
    private ListView completedTasks;
    private ArrayAdapter<String> mAdapter;
    private int numComplete;
    private int getNumCompleteToday;
    DBHelper helper;
    PopupWindow popUp;
    LinearLayout layout;
    public SettingsFragment() {
        // Required empty public constructor
    }

    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Dark = "darkKey";
    public static final String Notification = "notificationKey";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        prefs = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // Dark theme
        toggleDark = view.findViewById(R.id.toggleTheme);
        if (prefs.getBoolean(Dark, true)) {
            toggleDark.setChecked(true);
        } else {
            toggleDark.setChecked(false);
        }
        toggleDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.edit().putBoolean(Dark, isChecked).commit();
            }
        });

        // Notifications
        toggleNotifs = view.findViewById(R.id.toggleNotifs);
        if (prefs.getBoolean(Notification, true)) {
            toggleNotifs.setChecked(true);
        } else {
            toggleNotifs.setChecked(false);
        }
        toggleNotifs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.edit().putBoolean(Notification, isChecked).commit();
            }
        });

        //login/logout
        loginLogout = (Button)view.findViewById(R.id.loginLogout);
        loginLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(view.getContext(),LoginActivity.class);
                AccessToken.setCurrentAccessToken(null);
                startActivity(i);
            }
        });
        helper = new DBHelper(view.getContext());
        reset = (Button)view.findViewById(R.id.reset);
        popUp = new PopupWindow(getContext());
        layout = new LinearLayout(getContext());
        completedTasks = (ListView) view.findViewById(R.id.completedTasks);

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

        loadTaskList();
        refreshList();




        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showPopup();
            }
        });





        return view;
    }


    private void showToast(String content) {
        Toast toast;
        toast = Toast.makeText(this.getContext(), content, Toast.LENGTH_SHORT);
        toast.setMargin(0, (float)0.07);
        toast.show();
    }

    private void showPopup() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater2 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater2.inflate(R.layout.popup, (ViewGroup) view.findViewById(R.id.popup_1));
            popUp = new PopupWindow(layout, 300, 370, true);
            popUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Close = (Button) layout.findViewById(R.id.close_popup);
            Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUp.dismiss();
                    helper.deleteAllTasks();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void refreshList() {
        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<Task> tasksComplete = helper.getTaskListByPriority();
        ArrayList<String> tasks = new ArrayList<>();
        String output;
        for(Task t : tasksComplete) {
            if(t.isComplete() == 1) {
                output = t.getName();
                tasks.add(output);
            }
        }

        //if(mAdapter==null){
        mAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.row_simple,R.id.list_taskName_Simple,tasks);
        completedTasks.setAdapter(mAdapter);
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
