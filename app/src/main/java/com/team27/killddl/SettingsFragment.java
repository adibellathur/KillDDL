package com.team27.killddl;


import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.widget.TextView;
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
    private TextView numTaskComplete;
    private int numComplete;
    private int getNumCompleteToday;
    private Button completedStats;
    private TextView name;
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

                // Force app to restart
                Intent intent = getActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName() );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
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
        completedStats = (Button) view.findViewById(R.id.completedStats);

        completedStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(view.getContext(),CompletedTaskView.class);
                AccessToken.setCurrentAccessToken(null);
                startActivity(i);
            }
        });
        //Intent inte = getIntent();
        name = (TextView) view.findViewById(R.id.name);
        String nm = getArguments().getString("username");
        if(nm == null) nm = "Guest";
        name.setText("Welcome, " + nm +  "!");




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







}
