package com.team27.killddl;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    View view;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;

    DayCalendarFragment dayCalendarFragment;
    MonthCalendarFragment monthCalendarFragment;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.calendar_tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.calendar_viewpager);
        adapter = new ViewPageAdapter(getChildFragmentManager());

        dayCalendarFragment = new DayCalendarFragment();
        monthCalendarFragment = new MonthCalendarFragment();

        adapter.AddFragment(dayCalendarFragment, "Today");
        adapter.AddFragment(monthCalendarFragment, "Month");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    public void refreshTaskLists() {
        dayCalendarFragment.refreshList();
        monthCalendarFragment.refreshList();
    }
}
