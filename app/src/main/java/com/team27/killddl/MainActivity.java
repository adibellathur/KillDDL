package com.team27.killddl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.team27.killddl.data.DBHelper;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private FloatingActionButton fab;
    private DBHelper helper;
    FragmentManager manager;

    public CalendarFragment calendarFragment;
    public ImportantFragment importantFragment;
    public SettingsFragment settingsFragment;

    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Dark = "darkKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (prefs.getBoolean(Dark, true)) {
            setTheme(R.style.Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarFragment = new CalendarFragment();
        importantFragment = new ImportantFragment();
        settingsFragment = new SettingsFragment();


        toolbar = getSupportActionBar();
        toolbar.setElevation(0);
        toolbar.setTitle("Calendar");
        loadFragment(calendarFragment);

        helper = new DBHelper(this);

        fab = (FloatingActionButton) findViewById(R.id.fab_add);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_calendar:
                        toolbar.setTitle("Calendar");
                        calendarFragment.refreshTaskLists();
                        loadFragment(calendarFragment);
//                        showToast("Calendar pressed");
                        break;
                    case R.id.action_important:
                        toolbar.setTitle("Important");
                        loadFragment(importantFragment);
//                        showToast("Important pressed");
                        break;
                    case R.id.action_settings:
                        toolbar.setTitle("Settings");
                        Bundle data = new Bundle();
                        Intent inte = getIntent();

                        data.putString("username", inte.getStringExtra("username"));
                        settingsFragment.setArguments(data);
                        loadFragment(settingsFragment);
//                        showToast("Settings pressed");
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


//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
//        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 5);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
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

//    public void deleteTask(View view){
//        View parent = (View)view.getParent();
//        TextView taskTextView = (TextView) parent.findViewById(R.id.list_taskName);
//        String task = String.valueOf(taskTextView.getText());
//        helper.deleteTask(task);
//        calendarFragment.refreshTaskLists();
//    }
}
