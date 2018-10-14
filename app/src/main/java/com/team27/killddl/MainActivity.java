package com.team27.killddl;

import android.app.Service;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import layout.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Calendar");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_calendar:
                        toolbar.setTitle("Calendar");
                        loadFragment(new CalendarFragment());
                        showToast("Calendar pressed");
                        break;
                    case R.id.action_notification:
                        toolbar.setTitle("Notifications");
                        loadFragment(new NotificationsFragment());
                        showToast("Notifications pressed");
                        break;
                    case R.id.action_settings:
                        toolbar.setTitle("Settings");
                        loadFragment(new SettingsFragment());
                        showToast("Settings pressed");
                        break;
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.contentLayout, fragment,
                fragment.getTag()).commit();
    }

    private void showToast(String content) {
        Toast toast;
        toast = Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT);
        toast.setMargin(0, (float)0.07);
        toast.show();
    }
}
