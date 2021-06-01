package com.example.hotplego.ui.manager.management;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hotplego.R;
import com.example.hotplego.ui.manager.management.fragment.management_weekday;
import com.example.hotplego.ui.manager.management.fragment.management_weekend;

public class Management extends AppCompatActivity {

    Fragment weekday;
    Fragment weekend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.management);

        Spinner spinner = findViewById(R.id.management_spinner);

        weekday = new management_weekday();
        weekend = new management_weekend();

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
    }
    private void initSpinner(String selected) {
        switch (selected) {
            case "평일":
                setFragment(weekday);
                break;
            case "주말":
                setFragment(weekend);
        }
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}
