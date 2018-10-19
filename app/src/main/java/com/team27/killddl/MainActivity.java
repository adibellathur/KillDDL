package com.team27.killddl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.team27.killddl.data.DBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private FloatingActionButton fab;
    private DBHelper helper;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();
        toolbar.setElevation(0);
        toolbar.setTitle("Calendar");
        loadFragment(new CalendarFragment());

        helper = new DBHelper(this);

        fab = (FloatingActionButton) findViewById(R.id.fab_add);

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), CreateTaskActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.contentLayout, fragment,
                fragment.getTag()).commit();
    }


    private void showToast(String content) {
        Toast toast;
        toast = Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT);
        toast.setMargin(0, (float)0.07);
        toast.show();
    }

    public void deleteTask(View view){
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.list_taskName);
        String task = String.valueOf(taskTextView.getText());
        helper.deleteTask(task);

    }
}
