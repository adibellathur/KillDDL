package com.team27.killddl;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team27.killddl.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayCalendarFragment extends Fragment {

    View view;

    public DayCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_day_calendar, container, false);
        return view;
    }

}
