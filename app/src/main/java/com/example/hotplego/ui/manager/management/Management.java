package com.example.hotplego.ui.manager.management;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hotplego.R;
import com.example.hotplego.ui.manager.management.fragment.ManagementTime;

public class Management extends Fragment {

    Fragment weekday;
    Fragment sunday;
    Fragment saturday;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.management, container, false);

        Spinner spinner = (Spinner) v.findViewById(R.id.management_spinner);

        weekday = new ManagementTime("평일");
        sunday = new ManagementTime("토요일");
        saturday = new ManagementTime("일요일");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(spinner.getContext(),
                R.layout.management_tv, getResources().getStringArray(R.array.week));
        arrayAdapter.setDropDownViewResource(R.layout.management_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initSpinner(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return v;
    }

    private void initSpinner(String selected) {
        switch (selected) {
            case "평일":
                setFragment(weekday);
                break;
            case "토요일":
                setFragment(saturday);
                break;
            case "일요일":
                setFragment(sunday);
        }
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}
