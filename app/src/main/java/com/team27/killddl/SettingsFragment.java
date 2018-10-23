package com.team27.killddl;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

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
