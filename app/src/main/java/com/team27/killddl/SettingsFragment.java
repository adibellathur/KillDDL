package com.team27.killddl;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.team27.killddl.R;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class  SettingsFragment extends Fragment {

    private View view;
    private Button loginLogout;
    private Switch toggleDark;
    private Switch toggleNotifs;

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

        return view;
    }

}
